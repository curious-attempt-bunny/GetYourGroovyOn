package org.groovycasts.graffiti

import graffiti.Graffiti
import graffiti.Get
import groovy.json.JsonBuilder

@Get('/greet')
def greet() {
	params.name ? "Hey there, $params.name!" : "Hello!"
}

everyone = ['Bob', 'Jane']

@Get('/people')
def people() {
	def json = new JsonBuilder()
	
	json {
		people( everyone.collect { name -> [person:[name:name]] } )
	}
	
	json.toString()
}

Graffiti.serve this
Graffiti.start()