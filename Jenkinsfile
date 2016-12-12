/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

node {
	try {
		stage 'Checkout'
		checkout scm
		
		stage 'Gradle Build'
		try {
			sh "./gradlew clean build createLocalMavenRepo --refresh-dependencies --continue"
		} finally {
			step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/test/*.xml'])
		}
		
		stage 'Maven Build'
		def mvnHome = tool 'M3'
		env.M2_HOME = "${mvnHome}"
		dir('.m2/repository/org/eclipse/lsp4j') { deleteDir() }
		sh "${mvnHome}/bin/mvn -f releng --batch-mode --update-snapshots -Dmaven.repo.local=.m2/repository clean install"
		
		archive 'build/**'
		slackSend "Build Succeeded - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
		
	} catch (e) {
		slackSend color: 'danger', message: "Build Failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
		throw e
	}
}
