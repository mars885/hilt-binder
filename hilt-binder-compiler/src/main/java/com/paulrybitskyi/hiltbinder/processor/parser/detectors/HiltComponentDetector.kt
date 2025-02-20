/*
 * Copyright 2021 Paul Rybitskyi, oss@paulrybitskyi.com
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

import com.paulrybitskyi.hiltbinder.BindType.Component
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XProcessingEnv
import com.paulrybitskyi.hiltbinder.compiler.processing.XTypeElement
import com.paulrybitskyi.hiltbinder.processor.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.model.PredefinedHiltComponent
import com.paulrybitskyi.hiltbinder.processor.model.WITH_FRAGMENT_BINDINGS_TYPE_QUALIFIED_NAME
import com.paulrybitskyi.hiltbinder.processor.parser.HiltBinderException
import com.paulrybitskyi.hiltbinder.processor.parser.PredefinedHiltComponentMapper
import com.paulrybitskyi.hiltbinder.processor.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.utils.getBindAnnotation
import com.paulrybitskyi.hiltbinder.processor.utils.getBindAnnotationDefaultType
import com.paulrybitskyi.hiltbinder.processor.utils.getCustomComponentArg
import com.paulrybitskyi.hiltbinder.processor.utils.getInstallInArg
import com.paulrybitskyi.hiltbinder.processor.utils.getTypeUnsafely
import com.paulrybitskyi.hiltbinder.processor.utils.hasAnnotation
import com.paulrybitskyi.hiltbinder.processor.utils.typeElement

internal class HiltComponentDetector(
    private val processingEnv: XProcessingEnv,
    private val predefinedHiltComponentMapper: PredefinedHiltComponentMapper,
    private val messageProvider: MessageProvider,
) {

    fun detectComponent(annotatedElement: XTypeElement): HiltComponent {
        val componentInferredFromScope = inferFromScopeAnnotation(annotatedElement)
        val explicitComponent = detectExplicitComponent(annotatedElement)

        checkComponentMismatch(componentInferredFromScope, explicitComponent, annotatedElement)

        return (componentInferredFromScope ?: explicitComponent ?: returnDefaultComponent())
    }

    private fun inferFromScopeAnnotation(annotatedElement: XTypeElement): HiltComponent.Predefined? {
        if (shouldInstallInViewWithFragmentComponent(annotatedElement)) {
            return HiltComponent.Predefined(PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
        }

        return PredefinedHiltComponent.entries
            .firstOrNull {
                val scopeAnnotationType = processingEnv.getTypeUnsafely(it.scopeQualifiedName)
                val hasScopeAnnotation = annotatedElement.hasAnnotation(scopeAnnotationType)

                hasScopeAnnotation
            }
            ?.let(HiltComponent::Predefined)
    }

    private fun shouldInstallInViewWithFragmentComponent(annotatedElement: XTypeElement): Boolean {
        val viewScopeType = processingEnv.getTypeUnsafely(PredefinedHiltComponent.VIEW.scopeQualifiedName)
        val hasViewScope = annotatedElement.hasAnnotation(viewScopeType)

        val withFragmentBindingsType = processingEnv.getTypeUnsafely(
            WITH_FRAGMENT_BINDINGS_TYPE_QUALIFIED_NAME,
        )
        val hasWithFragmentBindingsAnno = annotatedElement.hasAnnotation(withFragmentBindingsType)

        return (hasViewScope && hasWithFragmentBindingsAnno)
    }

    private fun detectExplicitComponent(annotatedElement: XTypeElement): HiltComponent? {
        val bindAnnotation = annotatedElement.getBindAnnotation()
        val component = bindAnnotation.getInstallInArg()

        return when {
            (component == Component.NONE) -> null
            (component != Component.CUSTOM) -> detectExplicitPredefinedComponent(component)
            else -> detectExplicitCustomComponent(bindAnnotation, annotatedElement)
        }
    }

    private fun detectExplicitPredefinedComponent(component: Component): HiltComponent.Predefined {
        return HiltComponent.Predefined(predefinedHiltComponentMapper.mapToPredefinedComponent(component))
    }

    private fun detectExplicitCustomComponent(
        bindAnnotation: XAnnotation,
        annotatedElement: XTypeElement,
    ): HiltComponent.Custom {
        val defaultType = processingEnv.getBindAnnotationDefaultType()
        val customComponentType = checkNotNull(bindAnnotation.getCustomComponentArg(defaultType))

        if (customComponentType == defaultType) {
            throw HiltBinderException(
                messageProvider.undefinedCustomComponentError(),
                annotatedElement,
            )
        }

        return HiltComponent.Custom(customComponentType.typeElement)
    }

    private fun checkComponentMismatch(
        componentInferredFromScope: HiltComponent?,
        explicitComponent: HiltComponent?,
        annotatedElement: XTypeElement,
    ) {
        val mismatchExists = (
            (componentInferredFromScope != null) &&
            (explicitComponent != null) &&
            (componentInferredFromScope != explicitComponent)
        )

        if (mismatchExists) {
            throw HiltBinderException(messageProvider.componentMismatchError(), annotatedElement)
        }
    }

    private fun returnDefaultComponent(): HiltComponent {
        return HiltComponent.Predefined(PredefinedHiltComponent.SINGLETON)
    }
}
