package com.example.stripedemokotlin.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class UnauthorisedUserException(message: String) : IOException(message)
class NoInternetException(message: String): IOException(message)