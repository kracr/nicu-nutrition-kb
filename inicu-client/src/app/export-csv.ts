export class ExportCsv {
	
	static convertToCSV(myObject : any) {		
		var finalCSVHeading = function() {
			var keys = "";
			var concatenator = "";
			for (var i = 0; i < Object.keys(myObject[0]).length; i++) {
				if (i > 0) {
					concatenator = ",";
				}
				keys += concatenator + Object.keys(myObject[0])[i];
			}
			return keys;
		}
		var finalCSVBody = function() {
			var keys = finalCSVHeading().split(",");
			var concatenator = "";
			var text = "";
			var row = "";
			var value = "";
			for (var i = 0; i < myObject.length; i++) {
				row = "";
				concatenator = "";

				for (var j = 0; j < keys.length; j++) {
					if (j > 0) {
						concatenator = ",";
					}
					value = "" + myObject[i][keys[j]];
					row += concatenator + value.replace(/,/g, ";");
				}
				text += "\n" + row;
			}
			return text;
		};
		var csvData = finalCSVHeading().toUpperCase() + finalCSVBody();
		csvData = csvData.replace(/null/g, "");			
		return csvData;
	}
}