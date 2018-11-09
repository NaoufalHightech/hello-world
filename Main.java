package com.quest.global.compare;

import de.redsix.pdfcompare.PdfComparator;

public class Main {

	public static void main(String[] args) {
		try {

			System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

			System.setProperty("imageCacheSizeCount", "30");

			System.setProperty("maxImageSizeInCache", "100000");
			System.setProperty("mergeCacheSizeMB", "100");
			System.setProperty("swapCacheSizeMB", "100");
			System.setProperty("documentCacheSizeMB", "200");
			System.setProperty("parallelProcessing", "true");
			System.setProperty("overallTimeoutInMinutes", "15");

			String actualFile = "./pdf_files/client/Automation_025_page2.pdf";
			String expectedFile = "./pdf_files/client/Automation_027_page2.PDF";
			String diffOutputFile = "./pdf_files/client/clientDiffOutputFile_4_page2.PDF";

			/*
			 * String actualFile = "./pdf_files/actual_test.pdf"; String expectedFile =
			 * "./pdf_files/expected_test.PDF"; String diffOutputFile =
			 * "./pdf_files/diffOutputFile_3.PDF";
			 */

			/*
			 * PdfComparator pdfCompObj = new PdfComparator(args[0], args[1]);
			 * 
			 * boolean outputTrue = pdfCompObj.compare().writeTo(args[2]);
			 */
			PdfComparator pdfCompObj = new PdfComparator(actualFile, expectedFile);

			boolean outputTrue = pdfCompObj.compare().writeTo(diffOutputFile);

			if (!outputTrue) {
				System.out.println("PDF is created");
			} else {
				System.out.println("PDF is not created");
			}

		} catch (Exception exe) {
			exe.printStackTrace();
			// System.gc();
		}

	}

}
