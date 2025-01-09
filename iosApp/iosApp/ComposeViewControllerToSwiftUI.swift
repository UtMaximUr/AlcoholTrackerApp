//
//  ComposeViewControllerToSwiftUI.swift
//  AlcoholTracker
//
//  Created by Максим Утюжников on 09.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

//import SwiftUI
//import composeApp
//
//struct ComposeViewControllerToSwiftUI: UIViewControllerRepresentable {
//    private let lifecycle: LifecycleRegistry
//    private let topSafeArea: Float
//    private let bottomSafeArea: Float
//    
//    init(lifecycle: LifecycleRegistry, topSafeArea: Float, bottomSafeArea: Float) {
//        self.lifecycle = lifecycle
//        self.topSafeArea = topSafeArea
//        self.bottomSafeArea = bottomSafeArea
//    }
//    
//    func makeUIViewController(context: Context) -> UIViewController {
//        return IosComposeAppKt.MainViewController(
//            lifecycle: lifecycle,
//            //module: SharedModule().module,
//            topSafeArea: topSafeArea,
//            bottomSafeArea: bottomSafeArea
//        )
//    }
//
//    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
//    }
//}
