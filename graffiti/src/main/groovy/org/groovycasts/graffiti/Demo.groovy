package org.groovycasts.graffiti

import graffiti.Graffiti
import graffiti.Get

@Get('/greet')
def greet() {
	params.name ? "Hey there, $params.name!" : "Hello!"
}

Graffiti.serve this
Graffiti.start()