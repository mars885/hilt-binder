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

package com.paulrybitskyi.hiltbinder.compiler.processing.javac

import com.paulrybitskyi.hiltbinder.common.utils.safeCast
import com.paulrybitskyi.hiltbinder.compiler.processing.XAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.XElement
import com.paulrybitskyi.hiltbinder.compiler.processing.XOriginatingElement
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XAnnotationFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.factories.XOriginatingElementFactory
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.getAnnotation
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.getPackageName
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.getType
import com.paulrybitskyi.hiltbinder.compiler.processing.javac.utils.simpleNameStr
import javax.lang.model.element.Element
import javax.lang.model.element.Parameterizable

internal abstract class JavacElement(
    protected val env: JavacProcessingEnv,
    open val delegate: Element
): XElement {


    override val packageName: String
        get() = env.elementUtils.getPackageName(delegate)

    override val simpleName: String
        get() = delegate.simpleNameStr

    override val qualifiedName: String?
        get() = null

    override val typeParameterCount: Int
        get() = (delegate.safeCast<Parameterizable>()?.typeParameters?.size ?: 0)

    override val originatingElement: XOriginatingElement
        get() = XOriginatingElementFactory.createJavacElement(delegate)

    override val annotations: Sequence<XAnnotation> by lazy {
        delegate.annotationMirrors
            .asSequence()
            .map { XAnnotationFactory.createJavacAnnotation(env, it) }
    }


    override fun getAnnotation(annotationQualifiedName: String): XAnnotation? {
        return env.elementUtils.getType(annotationQualifiedName)
            ?.let { annoType -> env.typeUtils.getAnnotation(delegate, annoType) }
            ?.let { annoMirror -> XAnnotationFactory.createJavacAnnotation(env, annoMirror) }
    }


}
