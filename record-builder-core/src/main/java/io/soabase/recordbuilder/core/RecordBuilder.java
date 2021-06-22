/**
 * Copyright 2019 Jordan Zimmerman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.soabase.recordbuilder.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Inherited
public @interface RecordBuilder {
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @Retention(RetentionPolicy.SOURCE)
    @Inherited
    @interface Include {
        Class<?>[] value();

        /**
         * Pattern used to generate the package for the generated class. The value
         * is the literal package name however two replacement values can be used. '@'
         * is replaced with the package of the Include annotation. '*' is replaced with
         * the package of the included class.
         *
         * @return package pattern
         */
        String packagePattern() default "@";
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    @Inherited
    @interface Options {
        /**
         * The builder class name will be the name of the record (prefixed with any enclosing class) plus this suffix. E.g.
         * if the record name is "Foo", the builder will be named "FooBuilder".
         */
        String suffix() default "Builder";

        /**
         * Used by {@code RecordInterface}. The generated record will have the same name as the annotated interface
         * plus this suffix. E.g. if the interface name is "Foo", the record will be named "FooRecord".
         */
        String interfaceSuffix() default "Record";

        /**
         * The name to use for the copy builder
         */
        String copyMethodName() default "builder";

        /**
         * The name to use for the builder
         */
        String builderMethodName() default "builder";

        /**
         * The name to use for the build method
         */
        String buildMethodName() default "build";

        /**
         * The name to use for the downcast method
         */
        String downCastMethodName() default "_downcast";

        /**
         * The name to use for the method that returns the record components as a stream
         */
        String componentsMethodName() default "stream";

        /**
         * The name to use for the nested With class
         */
        String withClassName() default "With";

        /**
         * The prefix to use for the methods in the With class
         */
        String withClassMethodPrefix() default "with";

        /**
         * Return the comment to place at the top of generated files. Return null or an empty string for no comment.
         */
        String fileComment() default "Auto generated by io.soabase.recordbuilder.core.RecordBuilder: https://github.com/Randgalt/record-builder";

        /**
         * Return the file indent to use
         */
        String fileIndent() default "    ";

        /**
         * If the record is declared inside of another class, the outer class's name will
         * be prefixed to the builder name if this returns true.
         */
        boolean prefixEnclosingClassNames() default true;

        /**
         * If true, any annotations (if applicable) on record components are copied
         * to the builder methods
         *
         * @return true/false
         */
        boolean inheritComponentAnnotations() default true;

        /**
         * Set the default value of {@code Optional} record components to
         * {@code Optional.empty()}
         */
        boolean emptyDefaultForOptional() default true;
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.ANNOTATION_TYPE)
    @Inherited
    @interface Template {
        RecordBuilder.Options options();
    }
}
