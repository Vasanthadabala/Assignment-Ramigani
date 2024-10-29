package com.example.assignment.nav

interface Destinatins{
    val route:String
}

object screen1:Destinatins{
    override val route = "screen1"
}

object screen2:Destinatins{
    override val route = "screen2"
}