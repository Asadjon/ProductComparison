package com.cyberpantera.lib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes("com.cyberpantera.lib.DataAnnotation")
public class DataAnnotationProcessor extends AbstractProcessor {
    private static final String PACKAGE_NAME = "com.cyberpantera.productcomparison.generated";
    private static final String DATA_ANNOTATIONS_CLASS_NAME = "DataAnnotationsGenerated";
    private static final String DATA_ANNOTATION_CLASS_NAME = "DataAnnotation";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        TypeSpec.Builder dataAnnotationsClass = TypeSpec.classBuilder(DATA_ANNOTATIONS_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC);

        StringBuilder format = new StringBuilder("List.of(");
        List<List<Object>> args = new ArrayList<>();
        ClassName dataAnnotationClass = ClassName.get("", DATA_ANNOTATION_CLASS_NAME);

        for (TypeElement typeElement : annotations)
            for (Element element : roundEnvironment.getElementsAnnotatedWith(typeElement)) {
                DataAnnotation annotation = element.getAnnotation(DataAnnotation.class);

                format.append(format.lastIndexOf(")") > -1 ? ", " : "")
                        .append("new $T($L, $L, $S, $S, $L, $T.class)");

                args.add(List.of(dataAnnotationClass,
                        annotation.nameIndex(),
                        annotation.dailyWorkingHours(),
                        annotation.drawablePath(),
                        annotation.jsonPath(),
                        annotation.paramsResId().isEmpty() ? 0 : "com.cyberpantera.productcomparison." + annotation.paramsResId(),
                        element.asType()));
            }

        args.sort(Comparator.comparingInt(objects -> (int) objects.get(1)));

        ParameterizedTypeName listOfInnerGeneratedClass = ParameterizedTypeName.get(ClassName.get(List.class), dataAnnotationClass);
        FieldSpec.Builder dataAnnotationListField = FieldSpec.builder(listOfInnerGeneratedClass, "dataAnnotationList")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(format.append(')').toString(), args.stream().flatMap(List::stream).toArray());

        dataAnnotationsClass.addField(dataAnnotationListField.build());
        dataAnnotationsClass.addType(generateDataValueClass());
        generateGeneratedClass(dataAnnotationsClass.build());
        return true;
    }

    private TypeSpec generateDataValueClass() {
        TypeSpec.Builder dataAnnotationClass = TypeSpec.classBuilder(DATA_ANNOTATION_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(ClassName.get("lombok", "AllArgsConstructor"))
                .addAnnotation(ClassName.get("lombok", "Setter"))
                .addAnnotation(ClassName.get("lombok", "Getter"))
                .addAnnotation(ClassName.get("lombok", "ToString"))

                .addField(Integer.class, "nameIndex", Modifier.PRIVATE, Modifier.FINAL)
                .addField(Integer.class, "dailyWorkingHours", Modifier.PRIVATE, Modifier.FINAL)
                .addField(String.class, "drawablePath", Modifier.PRIVATE, Modifier.FINAL)
                .addField(String.class, "jsonPath", Modifier.PRIVATE, Modifier.FINAL)
                .addField(FieldSpec.builder(Integer.class, "paramResId", Modifier.PRIVATE, Modifier.FINAL).addAnnotation(ClassName.get("androidx.annotation", "IdRes")).build())
                .addField(Type.class, "dataType", Modifier.PRIVATE, Modifier.FINAL);

        return dataAnnotationClass.build();
    }

    private void generateGeneratedClass(TypeSpec dataAnnotationsClass) {

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, dataAnnotationsClass)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
