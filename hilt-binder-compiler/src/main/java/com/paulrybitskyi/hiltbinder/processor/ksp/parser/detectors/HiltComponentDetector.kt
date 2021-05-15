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

package com.paulrybitskyi.hiltbinder.processor.ksp.parser.detectors

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.ksp.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.ksp.model.PredefinedHiltComponent
import com.paulrybitskyi.hiltbinder.processor.ksp.model.WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.PredefinedHiltComponentMapper
import com.paulrybitskyi.hiltbinder.processor.ksp.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.ksp.utils.*

internal class HiltComponentDetector(
    private val resolver: Resolver,
    private val predefinedHiltComponentMapper: PredefinedHiltComponentMapper,
    private val messageProvider: MessageProvider
) {


    fun detectComponent(annotatedSymbol: KSClassDeclaration): HiltComponent {
        val componentInferredFromScope = inferFromScopeAnnotation(annotatedSymbol)
        val explicitComponent = detectExplicitComponent(annotatedSymbol)

        checkComponentMismatch(componentInferredFromScope, explicitComponent, annotatedSymbol)

        return (componentInferredFromScope ?: explicitComponent ?: returnDefaultComponent())
    }


    private fun inferFromScopeAnnotation(annotatedElement: KSClassDeclaration): HiltComponent.Predefined? {
        if(shouldInstallInViewWithFragmentComponent(annotatedElement)) {
            return HiltComponent.Predefined(PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
        }

        return PredefinedHiltComponent.values()
            .firstOrNull {
                val scopeAnnotationType = resolver.getTypeByName(it.scopeQualifiedName)
                val hasScopeAnnotation = annotatedElement.hasAnnotation(scopeAnnotationType)

                hasScopeAnnotation
            }
            ?.let(HiltComponent::Predefined)
    }


    private fun shouldInstallInViewWithFragmentComponent(annotatedElement: KSClassDeclaration): Boolean {
        val viewScopeType = resolver.getTypeByName(PredefinedHiltComponent.VIEW.scopeQualifiedName)
        val hasViewScope = annotatedElement.hasAnnotation(viewScopeType)

        val withFragmentBindingsType = resolver.getTypeByName(WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME)
        val hasWithFragmentBindingsAnno = annotatedElement.hasAnnotation(withFragmentBindingsType)

        return (hasViewScope && hasWithFragmentBindingsAnno)
    }


    private fun detectExplicitComponent(annotatedSymbol: KSClassDeclaration): HiltComponent? {
        val bindAnnotation = resolver.getBindAnnotation(annotatedSymbol)
        val component = bindAnnotation.getInstallInArg()

        return when {
            (component == BindType.Component.NONE) -> null
            (component != BindType.Component.CUSTOM) -> detectExplicitPredefinedComponent(component)
            else -> detectExplicitCustomComponent(bindAnnotation, annotatedSymbol)
        }
    }


    private fun detectExplicitPredefinedComponent(component: BindType.Component): HiltComponent.Predefined {
        return HiltComponent.Predefined(predefinedHiltComponentMapper.mapToPredefinedComponent(component))
    }


    private fun detectExplicitCustomComponent(
        bindAnnotation: KSAnnotation,
        annotatedSymbol: KSClassDeclaration
    ): HiltComponent.Custom {
        val customComponentType = bindAnnotation.getCustomComponentArg()
        val nothingType = resolver.builtIns.nothingType

        if((customComponentType == null) || customComponentType == nothingType) {
            throw HiltBinderException(
                messageProvider.undefinedCustomComponentError(),
                annotatedSymbol
            )
        }

        return HiltComponent.Custom(customComponentType.classDeclaration)
    }


    private fun checkComponentMismatch(
        componentInferredFromScope: HiltComponent?,
        explicitComponent: HiltComponent?,
        annotatedSymbol: KSClassDeclaration
    ) {
        val mismatchExists = (
            (componentInferredFromScope != null) &&
            (explicitComponent != null) &&
            (componentInferredFromScope != explicitComponent)
        )

        if(mismatchExists) {
            throw HiltBinderException(messageProvider.componentMismatchError(), annotatedSymbol)
        }
    }


    private fun returnDefaultComponent(): HiltComponent {
        return HiltComponent.Predefined(PredefinedHiltComponent.SINGLETON)
    }


}