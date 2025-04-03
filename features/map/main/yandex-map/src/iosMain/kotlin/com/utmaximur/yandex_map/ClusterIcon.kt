package com.utmaximur.yandex_map

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsImageRenderer
import platform.UIKit.UIImage
import platform.UIKit.UILabel
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
fun uiImage(name: String, color: UIColor = UIColor.whiteColor): UIImage {
    val size = CGRectMake(0.0, 0.0, 18.0, 18.0)

    val text = UILabel(size)
    text.text = name
    text.textColor = color
    text.font = UIFont.boldSystemFontOfSize(11.0)
    text.textAlignment = NSTextAlignmentCenter
    text.translatesAutoresizingMaskIntoConstraints = false

    val view = UIView(size)
    view.layer.backgroundColor = UIColor.redColor.CGColor
    view.layer.cornerRadius = 10.0
    view.translatesAutoresizingMaskIntoConstraints = true

    view.addSubview(text)

    NSLayoutConstraint.activateConstraints(
        listOf(
            text.centerXAnchor.constraintEqualToAnchor(view.centerXAnchor, constant = 10.0),
            text.centerYAnchor.constraintEqualToAnchor(view.centerYAnchor, constant = 10.0)
        )
    )

    val renderer = UIGraphicsImageRenderer(bounds = CGRectMake(0.0, 0.0, 22.0, 22.0))
    return renderer.imageWithActions { rendererContext ->
        if (rendererContext != null) {
            view.layer.renderInContext(rendererContext.CGContext)
        }
    }
}