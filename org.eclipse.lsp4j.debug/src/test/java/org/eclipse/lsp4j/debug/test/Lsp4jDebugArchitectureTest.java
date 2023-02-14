package org.eclipse.lsp4j.debug.test;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.base.DescribedPredicate.not;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Test to make sure we don't depend to xbase.lib, xtend.lib and guava at
 * runtime
 * 
 * @author Christian Dietrich
 */
public class Lsp4jDebugArchitectureTest {

	private static JavaClasses importedClasses;

	@BeforeClass
	public static void scan() {
		importedClasses = new ClassFileImporter().importPackages("org.eclipse.lsp4j.debug");
	}

	@AfterClass
	public static void cleanup() {
		importedClasses = null;
	}

	@Test
	public void testNoDependenyToXbaseLib() {
		assertNoDependencyToPackage("org.eclipse.xtext..");
	}

	@Test
	public void testNoDependenyToXtendLib() {
		assertNoDependencyToPackage("org.eclipse.xtend..");
	}

	@Test
	public void testNoDependenyToXtend2Lib() {
		assertNoDependencyToPackage("org.eclipse.xtend2..");
	}

	@Test
	public void testNoDependenyToGuava() {
		assertNoDependencyToPackage("com.google.common..");
	}

	@Test
	public void testNoDependenyToGenerator() {
		assertNoDependencyToPackage("org.eclipse.lsp4j.generator..");
	}

	private void assertNoDependencyToPackage(String badPackage) {
		ArchRule rule = noClasses()
				.that(resideInAPackage("org.eclipse.lsp4j.debug..")
						.and(not(resideInAPackage("org.eclipse.lsp4j.generator.."))))
				.and(not(resideInAPackage("org.eclipse.lsp4j.debug.test.."))).should().dependOnClassesThat()
				.resideInAPackage(badPackage);
		rule.check(importedClasses);
	}

}
