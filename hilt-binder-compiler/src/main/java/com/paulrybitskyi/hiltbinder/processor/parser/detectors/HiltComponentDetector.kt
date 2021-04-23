/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.hiltbinder.processor.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.model.*
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.parser.PredefinedHiltComponentMapper
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.*
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class HiltComponentDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val predefinedHiltComponentMapper: PredefinedHiltComponentMapper,
    private val messageProvider: MessageProvider
) {


    fun detectComponent(annotatedElement: TypeElement): HiltComponent {
        val componentDeducedFromScope = detectFromScopeAnnotation(annotatedElement)
        val explicitComponent = detectExplicitlyComponent(annotatedElement)

        if((componentDeducedFromScope != null) && (explicitComponent != null)) {
            throw HiltBinderException(messageProvider.duplicatedComponentError(), annotatedElement)
        }

        return (componentDeducedFromScope ?: explicitComponent ?: returnDefaultComponent())
    }


    private fun detectFromScopeAnnotation(annotatedElement: TypeElement): HiltComponent? {
        val scopeType = elementUtils.getType(SCOPE_TYPE_CANON_NAME)
        val scopeAnnotation = (typeUtils.getAnnoMarkedWithSpecificAnno(annotatedElement, scopeType) ?: return null)
        val scopeAnnotationType = scopeAnnotation.annotationType

        return detectPredefinedComponentFromScopeAnno(annotatedElement, scopeAnnotationType)
            ?: detectCustomComponentFromScopeAnno(scopeAnnotationType)
    }


    private fun detectPredefinedComponentFromScopeAnno(
        annotatedElement: TypeElement,
        scopeAnnotationType: TypeMirror
    ): HiltComponent.Predefined? {
        if(shouldInstallInViewWithFragmentComponent(annotatedElement)) {
            return HiltComponent.Predefined(PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
        }

        return PredefinedHiltComponent.values()
            .firstOrNull { typeUtils.isSameType(scopeAnnotationType, elementUtils.getType(it.scopeQualifiedName)) }
            ?.let(HiltComponent::Predefined)

    }


    private fun shouldInstallInViewWithFragmentComponent(annotatedElement: TypeElement): Boolean {
        val viewScopeType = elementUtils.getType(PredefinedHiltComponent.VIEW.scopeQualifiedName)
        val hasViewScope = typeUtils.hasAnnotation(annotatedElement, viewScopeType)

        val withFragmentBindingsType = elementUtils.getType(WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME)
        val hasWithFragmentBindingsAnno = typeUtils.hasAnnotation(annotatedElement, withFragmentBindingsType)

        return (hasViewScope && hasWithFragmentBindingsAnno)
    }


    private fun detectCustomComponentFromScopeAnno(scopeAnnotationType: DeclaredType): HiltComponent.Custom {
        val defineComponentType = elementUtils.getType(DEFINE_COMPONENT_TYPE_CANON_NAME)
        val scopeAnnotationPackage = elementUtils.getPackageOf(scopeAnnotationType.asElement())
        val customComponentElement = scopeAnnotationPackage.enclosedElements
            .single {
                ((it.kind == ElementKind.CLASS) || (it.kind == ElementKind.INTERFACE)) &&
                typeUtils.hasAnnotation(it, defineComponentType) &&
                typeUtils.hasAnnotation(it, scopeAnnotationType)
            }
            .cast<TypeElement>()

        return HiltComponent.Custom(
            simpleName = customComponentElement.getSimpleNameStr(),
            qualifiedName = customComponentElement.getQualifiedNameStr()
        )
    }


    private fun detectExplicitlyComponent(annotatedElement: TypeElement): HiltComponent? {
        val bindAnnotation = annotatedElement.getAnnotation(BindType::class.java)

        return when {
            (bindAnnotation.installIn == BindType.Component.NONE) -> null
            (bindAnnotation.installIn != BindType.Component.CUSTOM) -> detectExplicitPredefinedComponent(bindAnnotation)
            else -> detectExplicitCustomComponent(bindAnnotation, annotatedElement)
        }
    }


    private fun detectExplicitPredefinedComponent(bindAnnotation: BindType): HiltComponent.Predefined {
        return HiltComponent.Predefined(
            predefinedHiltComponentMapper.mapToPredefinedComponent(
                bindAnnotation.installIn
            )
        )
    }


    private fun detectExplicitCustomComponent(
        bindAnnotation: BindType,
        annotatedElement: TypeElement
    ): HiltComponent.Custom {
        val customComponentType = elementUtils.getTypeSafely(bindAnnotation::customComponent)
        val voidType = elementUtils.getType(VOID_TYPE_CANON_NAME)

        if(typeUtils.isSameType(customComponentType, voidType)) {
            throw HiltBinderException(
                messageProvider.undefinedCustomComponentError(),
                annotatedElement
            )
        }

        val customComponentElement = typeUtils.asTypeElement(customComponentType)

        return HiltComponent.Custom(
            simpleName = customComponentElement.getSimpleNameStr(),
            qualifiedName = customComponentElement.getQualifiedNameStr()
        )
    }


    private fun returnDefaultComponent(): HiltComponent {
        return HiltComponent.Predefined(PredefinedHiltComponent.SINGLETON)
    }


}