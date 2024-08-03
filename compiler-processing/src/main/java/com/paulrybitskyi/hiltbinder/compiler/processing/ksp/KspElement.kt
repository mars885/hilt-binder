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

package com.paulrybitskyi.hiltbinder.compiler.processing.ksp

import com.google.devtools.ksp.symbol.KSDeclaration
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.XOriginatingElement
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XAnnotationFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XOriginatingElementFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.getAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.getTypeByName
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.packageNameStr
import com.paulrybitskyi.hiltbinder.compiler.processing.ksp.utils.simpleNameStr

internal abstract class KspElement(
    protected val env: KspProcessingEnv,
    open val delegate: KSDeclaration,
) : XElement {

    override val packageName: String
        get() = delegate.packageNameStr

    override val simpleName: String
        get() = delegate.simpleNameStr

    override val qualifiedName: String?
        get() = delegate.qualifiedName?.asString()

    override val typeParameterCount: Int
        get() = delegate.typeParameters.size

    override val originatingElement: XOriginatingElement?
        get() = delegate.containingFile?.let(XOriginatingElementFactory::createKspElement)

    override val annotations: Sequence<XAnnotation> by lazy {
        delegate.annotations
            .map { XAnnotationFactory.createKspAnnotation(env, it) }
    }

    override fun getAnnotation(annotationQualifiedName: String): XAnnotation? {
        return env.resolver.getTypeByName(annotationQualifiedName)
            ?.let { annoType -> delegate.getAnnotation(annoType) }
            ?.let { annotation -> XAnnotationFactory.createKspAnnotation(env, annotation) }
    }
}
