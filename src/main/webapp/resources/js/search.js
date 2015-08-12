function drawRegionsMap() {

	var data = google.visualization.arrayToDataTable([
          ['Cities', 'Name', 'Routers'],
		  ['CO-ANT','Antioquia', 54],
          ['CO-TOL','Tolima', 2],
          ['CO-DC','Bogota', 90],
          ['CO-SAN','Santander', 4],
          ['CO-ATL','Atlantico', 9] 
    ]);

    var options = {
    		region: 'CO',
    		displayMode: 'regions',
    		resolution: 'provinces',
    		backgroundColor: '#B9DCFF',
    		datalessRegionColor: '#F4F3F2',
    		colorAxis: {colors: ['#FF0000','#B40404']}
    };

    var geomap = new google.visualization.GeoChart(document.getElementById('regions_div'));
    geomap.draw(data, options);
    $("#div_step_two").hide();
	google.visualization.events.addListener(geomap, 'regionClick',function(eventData){
	   alert(eventData.region);
		if (eventData.region == 'CO-DC'){		
			window.location.href = 'detail.xhtml';			
		}
	});
	
	
}