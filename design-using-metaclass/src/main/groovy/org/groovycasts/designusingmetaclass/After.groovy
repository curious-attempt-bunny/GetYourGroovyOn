package org.groovycasts.designusingmetaclass

import javax.media.jai.JAI
import javax.media.jai.PlanarImage
import java.awt.image.renderable.ParameterBlock
import javax.media.jai.KernelJAI
import javax.media.jai.operator.SubtractDescriptor

PlanarImage.metaClass.static.load << { String filename ->
    return JAI.create("fileload", filename)
}

PlanarImage.metaClass.getGrayScale = { ->
    ParameterBlock pb = new ParameterBlock()
    pb.addSource(delegate)
    pb.add([[ 0.114D, 0.587D, 0.299D, 0.0D ]] as double[][])

    return JAI.create("bandcombine", pb, null)
}

PlanarImage.metaClass.getEdgeDetect = { ->
     float[] data_h = [ 1.0F,   0.0F,   -1.0F,
                        1.414F, 0.0F,   -1.414F,
                        1.0F,   0.0F,   -1.0F ] as float[]
     float[] data_v = [-1.0F,  -1.414F, -1.0F,
                        0.0F,   0.0F,    0.0F,
                        1.0F,   1.414F,  1.0F] as float[]

     KernelJAI kern_h = new KernelJAI(3,3,data_h)
     KernelJAI kern_v = new KernelJAI(3,3,data_v)

     return JAI.create("gradientmagnitude", delegate,
                                      kern_h, kern_v)
}

PlanarImage.metaClass.minus = { PlanarImage rhs ->
    return SubtractDescriptor.create(delegate, rhs, null)
}

PlanarImage.metaClass.save = { String filename ->
    JAI.create("filestore", delegate, filename)
}

PlanarImage image1 = PlanarImage.load("image1.jpg").grayScale.edgeDetect



PlanarImage image2 = PlanarImage.load("image2.jpg").grayScale.edgeDetect



PlanarImage output = image1 - image2

output.save("output.png")