package com.garethmurphy.dcutimetable.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableParser {

	public static ArrayList<HashMap<String, String>> parse(String url) {

		ArrayList<HashMap<String, String>> classes =
				new ArrayList<HashMap<String, String>>();
		
		// TODO: Fix error handling to not just crash program.
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements mainTable = doc.select("table[border=1]");

			for (Element td : mainTable.select("td[id=object-cell-border]")) {

				HashMap<String, String> cDetail = new HashMap<String, String>();

				// The type of class, such as "Lec.", "Sem." and "Tut."
				cDetail.put("type", td.ownText().trim());

				// Details are stored in a format of inner tables.
				Elements iTables = td.select("table");

				// The room number, such as "HG15" and "L101"
				cDetail.put("roomNo", iTables.first().select("td").text());

				// The lecturer name.
				cDetail.put("lecturer", iTables.get(1).select("td[align=left]")
						.text().trim());

				// The class name. TODO: Normalise these to be standard title
				// case.
				cDetail.put("className",
						iTables.get(1).select("td[align=center]").text().trim());

				// This is the class-code, such as "CA165" and "MS121"
				String fullCode = iTables.last().select("td[align=left]")
						.text();
				cDetail.put("classCode",
						fullCode.substring(0, fullCode.indexOf('[')));

				// The weeks this class is on.
				cDetail.put("weeks", iTables.last().select("td[align=right]")
						.text().trim());

				classes.add(cDetail);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
}
