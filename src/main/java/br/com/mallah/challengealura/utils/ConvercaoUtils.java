package br.com.mallah.challengealura.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvercaoUtils {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static LocalDate stringToDate(String date) {
		return LocalDate.parse(date, formatter);
	}
	
	public static String dateToString(LocalDate date) {
		return formatter.format(date);
	}
}
