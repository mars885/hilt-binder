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

package com.paulrybitskyi.hiltbinder.processor.javac.parser.detectors

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.javac.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.javac.model.PredefinedHiltComponent
import com.paulrybitskyi.hiltbinder.processor.javac.model.VOID_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.model.WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.javac.parser.PredefinedHiltComponentMapper
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.javac.utils.*
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class HiltComponentDetector(
    private val elementUtils: Elements,
    private val typeUtils: Types,
    private val predefinedHiltComponentMapper: PredefinedHiltComponentMapper,
    private val messageProvider: MessageProvider
) {


    fun detectComponent(annotatedElement: TypeElement): HiltComponent {
        val componentInferredFromScope = inferFromScopeAnnotation(annotatedElement)
        val explicitComponent = detectExplicitComponent(annotatedElement)

        checkComponentMismatch(componentInferredFromScope, explicitComponent, annotatedElement)

        return (componentInferredFromScope ?: explicitComponent ?: returnDefaultComponent())
    }


    private fun inferFromScopeAnnotation(annotatedElement: TypeElement): HiltComponent.Predefined? {
        if(shouldInstallInViewWithFragmentComponent(annotatedElement)) {
            return HiltComponent.Predefined(PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
        }

        return PredefinedHiltComponent.values()
            .firstOrNull {
                val scopeAnnotationType = elementUtils.getType(it.scopeQualifiedName)
                val hasScopeAnnotation = typeUtils.hasAnnotation(annotatedElement, scopeAnnotationType)

                hasScopeAnnotation
            }
            ?.let(HiltComponent::Predefined)
    }


    private fun shouldInstallInViewWithFragmentComponent(annotatedElement: TypeElement): Boolean {
        val viewScopeType = elementUtils.getType(PredefinedHiltComponent.VIEW.scopeQualifiedName)
        val hasViewScope = typeUtils.hasAnnotation(annotatedElement, viewScopeType)

        val withFragmentBindingsType = elementUtils.getType(WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME)
        val hasWithFragmentBindingsAnno = typeUtils.hasAnnotation(annotatedElement, withFragmentBindingsType)

        return (hasViewScope && hasWithFragmentBindingsAnno)
    }


    private fun detectExplicitComponent(annotatedElement: TypeElement): HiltComponent? {
        val bindAnnotation = annotatedElement.getBindAnnotation(elementUtils, typeUtils)
        val component = bindAnnotation.getInstallInArg()

        return when {
            (component == BindType.Component.NONE) -> null
            (component != BindType.Component.CUSTOM) -> detectExplicitPredefinedComponent(component)
            else -> detectExplicitCustomComponent(bindAnnotation, annotatedElement)
        }
    }


    private fun detectExplicitPredefinedComponent(component: BindType.Component): HiltComponent.Predefined {
        return HiltComponent.Predefined(predefinedHiltComponentMapper.mapToPredefinedComponent(component))
    }


    private fun detectExplicitCustomComponent(
        bindAnnotation: AnnotationMirror,
        annotatedElement: TypeElement
    ): HiltComponent.Custom {
        val customComponentType = bindAnnotation.getCustomComponentArg()
        val voidType = elementUtils.getType(VOID_TYPE_CANON_NAME)

        if((customComponentType == null) || typeUtils.isSameType(customComponentType, voidType)) {
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


    private fun checkComponentMismatch(
        componentInferredFromScope: HiltComponent?,
        explicitComponent: HiltComponent?,
        annotatedElement: TypeElement
    ) {
        val mismatchExists = (
            (componentInferredFromScope != null) &&
                (explicitComponent != null) &&
                (componentInferredFromScope != explicitComponent)
            )

        if(mismatchExists) {
            throw HiltBinderException(messageProvider.componentMismatchError(), annotatedElement)
        }
    }


    private fun returnDefaultComponent(): HiltComponent {
        return HiltComponent.Predefined(PredefinedHiltComponent.SINGLETON)
    }


}