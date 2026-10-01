package com.example.sumativa2seaa.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import java.util.Locale

private const val PROPINA_MAXIMA = 10.0

@Composable
fun PropinaScreen(modifier: Modifier = Modifier) {
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var porcentaje by remember { mutableStateOf("") }

    var validar by remember { mutableStateOf(false) }
    var propina by remember { mutableStateOf(0.0) }
    var total by remember { mutableStateOf(0.0) }

    // Mensajes de error (null = sin error)
    val errorProducto = if (validar && producto.isBlank()) "Campo obligatorio" else null
    val errorCantidad = when {
        !validar -> null
        cantidad.isBlank() -> "Campo obligatorio"
        (cantidad.toIntOrNull() ?: 0) <= 0 -> "Debe ser mayor a 0"
        else -> null
    }
    val errorPrecio = when {
        !validar -> null
        precio.isBlank() -> "Campo obligatorio"
        (precio.toDoubleOrNull() ?: 0.0) <= 0.0 -> "Debe ser mayor a 0"
        else -> null
    }
    val errorPorcentaje = when {
        !validar -> null
        porcentaje.isBlank() -> "Campo obligatorio"
        porcentaje.toDoubleOrNull() == null -> "Valor no válido"
        porcentaje.toDouble() > PROPINA_MAXIMA -> "No debe exceder el 10%"
        porcentaje.toDouble() < 0 -> "No puede ser negativo"
        else -> null
    }

    fun calcular() {
        validar = true
        val hayErrores = producto.isBlank() ||
                (cantidad.toIntOrNull() ?: 0) <= 0 ||
                (precio.toDoubleOrNull() ?: 0.0) <= 0.0 ||
                porcentaje.toDoubleOrNull() == null ||
                porcentaje.toDouble() !in 0.0..PROPINA_MAXIMA
        if (hayErrores) {
            propina = 0.0
            total = 0.0
            return
        }
        val subtotal = cantidad.toInt() * precio.toDouble()
        propina = subtotal * porcentaje.toDouble() / 100
        total = subtotal + propina
    }

    fun limpiar() {
        producto = ""
        cantidad = ""
        precio = ""
        porcentaje = ""
        validar = false
        propina = 0.0
        total = 0.0
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ResultadoColumna("Propina", formato(propina))
            ResultadoColumna("Total a pagar", formato(total))
        }

        Spacer(Modifier.height(16.dp))

        CampoTexto(producto, { producto = it }, "Producto", KeyboardType.Text, errorProducto)
        CampoTexto(cantidad, { cantidad = it }, "Cantidad", KeyboardType.Number, errorCantidad)
        CampoTexto(precio, { precio = it }, "Precio unitario", KeyboardType.Decimal, errorPrecio)
        CampoTexto(porcentaje, { porcentaje = it }, "Porcentaje de propina (máx. 10%)", KeyboardType.Decimal, errorPorcentaje)

        BotonPrincipal("Calcular total", onClick = { calcular() })
        BotonSecundario("Limpiar", onClick = { limpiar() })
    }
}

private fun formato(valor: Double): String = String.format(Locale.US, "$%.2f", valor)