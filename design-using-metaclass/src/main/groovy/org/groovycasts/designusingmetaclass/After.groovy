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

void save(PlanarImage image, String filename) {
    JAI.create("filestore", image, filename)
}

PlanarImage image1 = PlanarImage.load("image1.jpg")
PlanarImage gray1 = image1.grayScale
PlanarImage edge1 = gray1.edgeDetect

PlanarImage image2 = PlanarImage.load("image2.jpg")
PlanarImage gray2 = image2.grayScale
PlanarImage edge2 = gray2.edgeDetect

PlanarImage output = edge1 - edge2

save(output, "output.png")