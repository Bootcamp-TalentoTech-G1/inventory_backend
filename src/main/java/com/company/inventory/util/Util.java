package com.company.inventory.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Util {

	// Método para comprimir los bytes de la imagen antes de almacenarla en la base de datos
	public static byte[] compressZLib(byte[] data) {
		// Crear un objeto Deflater para comprimir los datos
		Deflater deflater = new Deflater();
		deflater.setInput(data); // Establecer los datos de entrada
		deflater.finish(); // Indicar que se han introducido todos los datos

		// Crear un stream de salida para almacenar los datos comprimidos
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024]; // Buffer para leer los datos comprimidos
		while (!deflater.finished()) { // Mientras haya datos por comprimir
			int count = deflater.deflate(buffer); // Comprimir los datos y almacenarlos en el buffer
			outputStream.write(buffer, 0, count); // Escribir los datos comprimidos en el stream de salida
		}
		try {
			outputStream.close(); // Cerrar el stream de salida
		} catch (IOException e) {
			// Manejar cualquier excepción de IO
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray(); // Devolver los datos comprimidos como un array de bytes
	}

	// Método para descomprimir los bytes de la imagen antes de devolverla a la aplicación Angular
	public static byte[] decompressZLib(byte[] data) {
		// Crear un objeto Inflater para descomprimir los datos
		Inflater inflater = new Inflater();
		inflater.setInput(data); // Establecer los datos de entrada

		// Crear un stream de salida para almacenar los datos descomprimidos
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024]; // Buffer para leer los datos descomprimidos
		try {
			while (!inflater.finished()) { // Mientras haya datos por descomprimir
				int count = inflater.inflate(buffer); // Descomprimir los datos y almacenarlos en el buffer
				outputStream.write(buffer, 0, count); // Escribir los datos descomprimidos en el stream de salida
			}
			outputStream.close(); // Cerrar el stream de salida
		} catch (IOException ioe) {
			// Manejar cualquier excepción de IO
		} catch (DataFormatException e) {
			// Manejar cualquier excepción relacionada con el formato de los datos
		}
		return outputStream.toByteArray(); // Devolver los datos descomprimidos como un array de bytes
	}

}

