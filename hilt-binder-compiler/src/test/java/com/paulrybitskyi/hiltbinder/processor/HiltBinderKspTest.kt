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

package com.paulrybitskyi.hiltbinder.processor

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.hiltbinder.processor.javac.model.HiltComponent
import com.paulrybitskyi.hiltbinder.processor.javac.model.PredefinedHiltComponent
import com.paulrybitskyi.hiltbinder.processor.javac.model.WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME
import com.paulrybitskyi.hiltbinder.processor.javac.parser.PredefinedHiltComponentMapper
import com.paulrybitskyi.hiltbinder.processor.javac.parser.factories.ModuleInterfaceNameFactory
import com.paulrybitskyi.hiltbinder.processor.javac.parser.providers.MessageProvider
import com.paulrybitskyi.hiltbinder.processor.ksp.HiltBinderKspProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.Test
import java.io.File

internal class HiltBinderKspTest {


    private companion object {

        private val PREDEFINED_HILT_COMPONENT_MAPPER = PredefinedHiltComponentMapper()
        private val MODULE_INTERFACE_NAME_FACTORY = ModuleInterfaceNameFactory()
        private val MESSAGE_PROVIDER = MessageProvider()

        private val VALID_ANNOTATION_PREDEFINED_COMPONENTS = BindType.Component
            .values()
            .filter { (it != BindType.Component.NONE) && (it != BindType.Component.CUSTOM) }

    }


    @Test
    fun `Binds class implicitly to its single interface`() {
        val expectedModule = getFile("1/ExpectedModule.java")
        val compilation = setupCompilation("Testable.java", "1/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its single interface`() {
        val expectedModule = getFile("2/ExpectedModule.java")
        val compilation = setupCompilation("Testable.java", "2/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class implicitly to its superclass`() {
        val expectedModule = getFile("3/ExpectedModule.java")
        val compilation = setupCompilation("AbstractTest.java", "3/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its superclass`() {
        val expectedModule = getFile("4/ExpectedModule.java")
        val compilation = setupCompilation("AbstractTest.java", "4/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has two interfaces, to specific interface`() {
        val expectedModule = getFile("5/ExpectedModule.java")
        val compilation = setupCompilation(
            "5/Testable1.java",
            "5/Testable2.java",
            "5/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass and interface, to superclass`() {
        val expectedModule = getFile("6/ExpectedModule.java")
        val compilation = setupCompilation(
            "AbstractTest.java",
            "Testable.java",
            "6/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass and interface, to interface`() {
        val expectedModule = getFile("7/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "AbstractTest.java",
            "7/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to Object class`() {
        val expectedModule = getFile("8/ExpectedModule.java")
        val compilation = setupCompilation("8/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass, to Object class`() {
        val expectedModule = getFile("9/ExpectedModule.java")
        val compilation = setupCompilation("AbstractTest.java", "9/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has interface, to Object class`() {
        val expectedModule = getFile("10/ExpectedModule.java")
        val compilation = setupCompilation("Testable.java", "10/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass and interface, to Object class`() {
        val expectedModule = getFile("11/ExpectedModule.java")
        val compilation = setupCompilation(
            "AbstractTest.java",
            "Testable.java",
            "11/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass, to its superclass's implemented interface`() {
        val expectedModule = getFile("12/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "12/AbstractTest.java",
            "12/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has superclass, to its superclass's superclass`() {
        val expectedModule = getFile("13/ExpectedModule.java")
        val compilation = setupCompilation(
            "13/AbstractAbstractTest.java",
            "13/AbstractTest.java",
            "13/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly, which has interface, to its interface's base interface`() {
        val expectedModule = getFile("14/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "14/UnitTestable.java",
            "14/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class implicitly to its parameterized interface`() {
        val expectedModule = getFile("15/ExpectedModule.java")
        val compilation = setupCompilation(
            "15/Testable.java",
            "15/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its direct parameterized interface`() {
        val expectedModule = getFile("35/ExpectedModule.java")
        val compilation = setupCompilation(
            "35/Testable.java",
            "35/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its indirect parameterized interface`() {
        val expectedModule = getFile("37/ExpectedModule.java")
        val compilation = setupCompilation(
            "37/Testable.java",
            "37/UnitTestable.java",
            "37/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class implicitly to its parameterized superclass`() {
        val expectedModule = getFile("16/ExpectedModule.java")
        val compilation = setupCompilation(
            "16/AbstractTest.java",
            "16/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its direct parameterized superclass`() {
        val expectedModule = getFile("36/ExpectedModule.java")
        val compilation = setupCompilation(
            "36/AbstractTest.java",
            "36/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Binds class explicitly to its indirect parameterized superclass`() {
        val expectedModule = getFile("38/ExpectedModule.java")
        val compilation = setupCompilation(
            "38/AbstractAbstractTest.java",
            "38/AbstractTest.java",
            "38/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Fails to bind class implicitly, which does not have superclass or interface`() {
        val compilation = setupCompilation("17/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.undefinedReturnTypeError())
    }


    @Test
    fun `Fails to bind class implicitly, which has two interfaces`() {
        val compilation = setupCompilation(
            "18/Testable1.java",
            "18/Testable2.java",
            "18/Test.java"
        )
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.undefinedReturnTypeError())
    }


    @Test
    fun `Fails to bind class implicitly, which has superclass and interface`() {
        val compilation = setupCompilation(
            "AbstractTest.java",
            "Testable.java",
            "19/Test.java"
        )
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.undefinedReturnTypeError())
    }


    @Test
    fun `Fails to bind class explicitly, which does not have superclass or interface, to interface`() {
        val compilation = setupCompilation("Testable.java", "20/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.noSubtypeRelationError("Test", "Testable"))
    }


    @Test
    fun `Fails to bind class explicitly, which does not have superclass or interface, to class`() {
        val compilation = setupCompilation("AbstractTest.java", "21/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.noSubtypeRelationError("Test", "AbstractTest"))
    }


    @Test
    fun `Installs binding in predefined component, which is inferred from scope annotation`() {
        val returnType = getSourceFile("Testable.java")

        for(component in PredefinedHiltComponent.values()) {
            val isViewWithFragmentComponent = (component == PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
            val withFragmentBindingAnnotation = if(isViewWithFragmentComponent) {
                "@$WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME"
            } else {
                ""
            }

            val bindingType = SourceFile.java(
                "Test.java",
                """
                import com.paulrybitskyi.hiltbinder.BindType;
                
                @${component.scopeQualifiedName}
                $withFragmentBindingAnnotation
                @BindType
                public class Test implements Testable {}
                """.trimIndent()
            )
            val predefinedHiltComponent = HiltComponent.Predefined(component)
            val interfaceName = MODULE_INTERFACE_NAME_FACTORY.createInterfaceName(predefinedHiltComponent)
            val expectedModule = """
                // Generated by @BindType. Do not modify!
                
                import dagger.Binds;
                import dagger.Module;
                import dagger.hilt.InstallIn;
                import ${component.qualifiedName};
                
                @Module
                @InstallIn(${component.simpleName}.class)
                public interface $interfaceName {
                  @Binds
                  Testable bind_Test(Test binding);
                }
            """.trimIndent()

            val compilation = setupCompilation(returnType, bindingType)
            val result = compilation.compile()
            val generatedFile = File(compilation.kspSourcesDir, "$interfaceName.java")

            assertThat(result.exitCode).isEqualTo(ExitCode.OK)
            assertThat(generatedFile.exists()).isTrue()
            assertThat(generatedFile.readText()).isEqualTo(expectedModule)
        }
    }


    @Test
    fun `Installs binding in predefined component, which is explicitly specified in annotation`() {
        val returnType = getSourceFile("Testable.java")

        for(predefinedComponent in VALID_ANNOTATION_PREDEFINED_COMPONENTS) {
            val bindingType = SourceFile.java(
                "Test.java",
                """
                import com.paulrybitskyi.hiltbinder.BindType;
                
                @BindType(installIn = BindType.Component.${predefinedComponent.name})
                public class Test implements Testable {}
                """.trimIndent()
            )
            val mappedComponent = PREDEFINED_HILT_COMPONENT_MAPPER.mapToPredefinedComponent(predefinedComponent)
            val predefinedHiltComponent = HiltComponent.Predefined(mappedComponent)
            val interfaceName = MODULE_INTERFACE_NAME_FACTORY.createInterfaceName(predefinedHiltComponent)
            val expectedModule = """
                // Generated by @BindType. Do not modify!
                
                import dagger.Binds;
                import dagger.Module;
                import dagger.hilt.InstallIn;
                import ${mappedComponent.qualifiedName};
                
                @Module
                @InstallIn(${mappedComponent.simpleName}.class)
                public interface $interfaceName {
                  @Binds
                  Testable bind_Test(Test binding);
                }
                """.trimIndent()

            val compilation = setupCompilation(returnType, bindingType)
            val result = compilation.compile()
            val generatedFile = File(compilation.kspSourcesDir, "$interfaceName.java")

            assertThat(result.exitCode).isEqualTo(ExitCode.OK)
            assertThat(generatedFile.exists()).isTrue()
            assertThat(generatedFile.readText()).isEqualTo(expectedModule)
        }
    }


    @Test
    fun `Installs binding in predefined component, which is specified both by the scope annotation and explicitly`() {
        val returnType = getSourceFile("Testable.java")

        for(predefinedComponent in VALID_ANNOTATION_PREDEFINED_COMPONENTS) {
            val mappedComponent = PREDEFINED_HILT_COMPONENT_MAPPER.mapToPredefinedComponent(predefinedComponent)
            val isViewWithFragmentComponent = (mappedComponent == PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
            val withFragmentBindingAnnotation = if(isViewWithFragmentComponent) {
                "@$WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME"
            } else {
                ""
            }

            val bindingType = SourceFile.java(
                "Test.java",
                """
                import com.paulrybitskyi.hiltbinder.BindType;
                
                @${mappedComponent.scopeQualifiedName}
                $withFragmentBindingAnnotation
                @BindType(installIn = BindType.Component.${predefinedComponent.name})
                public class Test implements Testable {}
                """.trimIndent()
            )
            val predefinedHiltComponent = HiltComponent.Predefined(mappedComponent)
            val interfaceName = MODULE_INTERFACE_NAME_FACTORY.createInterfaceName(predefinedHiltComponent)
            val expectedModule = """
                // Generated by @BindType. Do not modify!
                
                import dagger.Binds;
                import dagger.Module;
                import dagger.hilt.InstallIn;
                import ${mappedComponent.qualifiedName};
                
                @Module
                @InstallIn(${mappedComponent.simpleName}.class)
                public interface $interfaceName {
                  @Binds
                  Testable bind_Test(Test binding);
                }
                """.trimIndent()

            val compilation = setupCompilation(returnType, bindingType)
            val result = compilation.compile()
            val generatedFile = File(compilation.kspSourcesDir, "$interfaceName.java")

            assertThat(result.exitCode).isEqualTo(ExitCode.OK)
            assertThat(generatedFile.exists()).isTrue()
            assertThat(generatedFile.readText()).isEqualTo(expectedModule)
        }
    }


    @Test
    fun `Fails to install binding in predefined component, when scope's component and explicit component differs`() {
        val returnType = getSourceFile("Testable.java")

        for(predefinedComponent in VALID_ANNOTATION_PREDEFINED_COMPONENTS) {
            val mappedComponent = PREDEFINED_HILT_COMPONENT_MAPPER.mapToPredefinedComponent(predefinedComponent)
            val isViewWithFragmentComponent = (mappedComponent == PredefinedHiltComponent.VIEW_WITH_FRAGMENT)
            val withFragmentBindingAnnotation = if(isViewWithFragmentComponent) {
                "@$WITH_FRAGMENT_BINDINGS_TYPE_CANON_NAME"
            } else {
                ""
            }
            val mismatchedExplicitComponent = when(mappedComponent) {
                PredefinedHiltComponent.SINGLETON -> PredefinedHiltComponent.ACTIVITY_RETAINED
                PredefinedHiltComponent.ACTIVITY_RETAINED -> PredefinedHiltComponent.SERVICE
                PredefinedHiltComponent.SERVICE -> PredefinedHiltComponent.ACTIVITY
                PredefinedHiltComponent.ACTIVITY -> PredefinedHiltComponent.VIEW_MODEL
                PredefinedHiltComponent.VIEW_MODEL -> PredefinedHiltComponent.FRAGMENT
                PredefinedHiltComponent.FRAGMENT -> PredefinedHiltComponent.VIEW
                PredefinedHiltComponent.VIEW -> PredefinedHiltComponent.VIEW_WITH_FRAGMENT
                PredefinedHiltComponent.VIEW_WITH_FRAGMENT -> PredefinedHiltComponent.SINGLETON
            }

            val bindingType = SourceFile.java(
                "Test.java",
                """
                import com.paulrybitskyi.hiltbinder.BindType;
                
                @${mappedComponent.scopeQualifiedName}
                $withFragmentBindingAnnotation
                @BindType(installIn = BindType.Component.${mismatchedExplicitComponent.name})
                public class Test implements Testable {}
                """.trimIndent()
            )

            val compilation = setupCompilation(returnType, bindingType)
            val result = compilation.compile()

            assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
            assertThat(result.messages).contains(MESSAGE_PROVIDER.componentMismatchError())
        }
    }


    @Test
    fun `Installs scoped binding in custom component successfully`() {
        val expectedModule = getFile("42/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "42/CustomComponent.java",
            "42/CustomScope.java",
            "42/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_CustomComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Installs unscoped binding in custom component successfully`() {
        val expectedModule = getFile("43/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "43/CustomComponent.java",
            "43/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_CustomComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Fails to install binding in custom component, when its type is unspecified`() {
        val compilation = setupCompilation("Testable.java", "44/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.undefinedCustomComponentError())
    }


    @Test
    fun `Binds class with qualifier to its single interface`() {
        val expectedModule = getFile("22/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "22/Test.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Fails to bind class, which is specified to have qualifier, but does not come have it`() {
        val compilation = setupCompilation("Testable.java", "23/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.qualifierAbsentError())
    }


    @Test
    fun `Saves bound classes into multibound set`() {
        val expectedModule = getFile("24/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "24/Test1.java",
            "24/Test2.java",
            "24/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound parameterized classes into multibound set`() {
        val expectedModule = getFile("39/ExpectedModule.java")
        val compilation = setupCompilation(
            "39/Testable.java",
            "39/Test1.java",
            "39/Test2.java",
            "39/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into qualified multibound set`() {
        val expectedModule = getFile("25/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "25/Test1.java",
            "25/Test2.java",
            "25/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Fails to save bound class, which does not have @MapKey annotation, into multibound map`() {
        val compilation = setupCompilation("Testable.java", "26/Test.java")
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(ExitCode.COMPILATION_ERROR)
        assertThat(result.messages).contains(MESSAGE_PROVIDER.noMapKeyError())
    }


    @Test
    fun `Saves bound classes into multibound map using standard integer key annotation`() {
        val expectedModule = getFile("27/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "27/Test1.java",
            "27/Test2.java",
            "27/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into multibound map using standard long key annotation`() {
        val expectedModule = getFile("28/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "28/Test1.java",
            "28/Test2.java",
            "28/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into multibound map using standard string key annotation`() {
        val expectedModule = getFile("29/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "29/Test1.java",
            "29/Test2.java",
            "29/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into multibound map using standard class key annotation`() {
        val expectedModule = getFile("30/ExpectedModule.java")
        val compilation = setupCompilation(
            "30/Test1.java",
            "30/Test2.java",
            "30/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound parameterized classes into multibound map using standard class key annotation`() {
        val expectedModule = getFile("40/ExpectedModule.java")
        val compilation = setupCompilation(
            "40/Testable.java",
            "40/Test1.java",
            "40/Test2.java",
            "40/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into multibound map using custom @MapKey annotation`() {
        val expectedModule = getFile("31/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "31/TestMapKey.java",
            "31/Test1.java",
            "31/Test2.java",
            "31/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound parameterized classes into multibound map using custom @MapKey annotation`() {
        val expectedModule = getFile("41/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "41/TestMapKey.java",
            "41/Test1.java",
            "41/Test2.java",
            "41/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Saves bound classes into qualified multibound map`() {
        val expectedModule = getFile("32/ExpectedModule.java")
        val compilation = setupCompilation(
            "Testable.java",
            "32/Test1.java",
            "32/Test2.java",
            "32/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Verify that binding method is properly formatted`() {
        val expectedModule = getFile("33/ExpectedModule.java")
        val compilation = setupCompilation("Testable.java", "33/Test.java")
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    @Test
    fun `Verify that common prefix package name is used based on bindings of component`() {
        val expectedModule = getFile("34/ExpectedModule.java")
        val compilation = setupCompilation(
            "34/Testable1.java",
            "34/Testable2.java",
            "34/Testable3.java",
            "34/Test1.java",
            "34/Test2.java",
            "34/Test3.java"
        )
        val result = compilation.compile()
        val generatedFile = File(compilation.kspSourcesDir, "HiltBinder_SingletonComponentModule.java")

        assertThat(result.exitCode).isEqualTo(ExitCode.OK)
        assertThat(generatedFile.exists()).isTrue()
        assertThat(generatedFile.readText()).isEqualTo(expectedModule.readText())
    }


    private fun setupCompilation(vararg filePaths: String): KotlinCompilation {
        return setupCompilation(*filePaths.map(::getSourceFile).toTypedArray())
    }


    private fun setupCompilation(vararg files: SourceFile): KotlinCompilation {
        val compilation = KotlinCompilation().apply {
            sources = files.toList()
            symbolProcessorProviders = listOf(HiltBinderKspProcessorProvider())
            verbose = false
            inheritClassPath = true
        }

        return compilation
    }


    private fun getSourceFile(path: String): SourceFile {
        return SourceFile.fromPath(getFile(path))
    }


    private fun getFile(path: String): File {
        return File(checkNotNull(javaClass.classLoader.getResource(path)).path)
    }


}