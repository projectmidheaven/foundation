package org.midheaven;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.midheaven.lang.ValueClass;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArchitectureConformityTestCases {

    JavaClasses allClasses;

    @BeforeAll
    public void setup(){
        allClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("org.midheaven");
    }
    @Test
    public void valuesClassesAreValueCompatible(){
        classes().that().areAnnotatedWith(ValueClass.class)
                .should().haveModifier(JavaModifier.FINAL).orShould().haveModifier(JavaModifier.ABSTRACT)
                .andShould().notBeRecords()
                .check(allClasses);

        constructors().that().areDeclaredInClassesThat().areAnnotatedWith(ValueClass.class)
                .should().notHaveModifier(JavaModifier.PUBLIC)
                .check(allClasses);

        fields().that().areDeclaredInClassesThat().areAnnotatedWith(ValueClass.class)
                .should().beFinal()
                .andShould().notHaveModifier(JavaModifier.PUBLIC)
                .check(allClasses);

        methods().that().areDeclaredInClassesThat().areAnnotatedWith(ValueClass.class)
                .should().beFinal().orShould().beDeclaredInClassesThat().haveModifier(JavaModifier.FINAL)
                .orShould().beDeclaredInClassesThat().haveModifier(JavaModifier.ABSTRACT)
                .andShould().notHaveModifier(JavaModifier.SYNCHRONIZED)
                .check(allClasses);
    }
}
