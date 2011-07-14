package org.groovycasts.graffiti

import graffiti.Graffiti
import graffiti.Get

@Get('/greet')
def greet() {
	"Hello!"
}

Graffiti.serve this
Graffiti.start()