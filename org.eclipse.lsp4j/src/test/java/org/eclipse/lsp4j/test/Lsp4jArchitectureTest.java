/******************************************************************************
 * Copyright (c) 2023 itemis AG and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test;

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
public class Lsp4jArchitectureTest {

	private static JavaClasses importedClasses;

	@BeforeClass
	public static void scan() {
		importedClasses = new ClassFileImporter().importPackages("org.eclipse.lsp4j");
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
				.that(resideInAPackage("org.eclipse.lsp4j..")
						.and(not(resideInAPackage("org.eclipse.lsp4j.generator.."))))
				.and(not(resideInAPackage("org.eclipse.lsp4j.test.."))).should().dependOnClassesThat()
				.resideInAPackage(badPackage);
		rule.check(importedClasses);
	}

}
