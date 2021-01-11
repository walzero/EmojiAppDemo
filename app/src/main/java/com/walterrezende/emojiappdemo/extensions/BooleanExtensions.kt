package com.walterrezende.emojiappdemo.extensions

inline fun Boolean.ifTrue(block: () -> Any) {
    this.takeIf { this }?.run { block() }
}

inline fun Boolean.ifFalse(block: () -> Any) {
    this.takeIf { this }?.run { block() }
}