var map;

function initialize() {
	var mapCanvas = document.getElementById('map-canvas');
	var mapOptions = {
		center : new google.maps.LatLng(4.6482975, -74.107807),
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		streetViewControl : false,
		disableDefaultUI : true,
	// minZoom : 7
	}
	map = new google.maps.Map(mapCanvas, mapOptions);
	map.data.loadGeoJson('../resources/geoJson/CO-VID.json');
	map.data.setStyle({
		fillColor : 'red',
		strokeColor : 'red',
		strokeWeight : 1,
		fillOpacity : 0.2
	});

	var markers = [
			{
				title : 'Bosa',
				position : {
					lat : 4.6256415,
					lng : -74.1876155
				},
				content : "<div style='border-collapse: collapse;'><h3 style='text-align: center;'>G4S CASH SOLUTIONS COLOMBIA LTDA.</h3><div class='contenedor'><div class='contenidos'><div class='columna title'>Tipo de Servicio</div>"
						+ "<div class='columna'>Tipo 1</div></div><div class='contenidos'><div class='columna title'>Punto Principal Cliente</div><div class='columna'>Si</div></div><div class='contenidos'>"
						+ "<div class='columna title'>Contacto</div><div class='columna'>Andres Vallen Jaime Gonzales<br />1-7054040 Ext 1913<br />310 888 8777<br />andres.vallen@co.g4s.com<br />Lun-Vie 8.00 am - 5.00 am"
						+ "</div></div><div class='contenidos'><div class='columna title'>Direccion</div><div class='columna'>Carrera 78 #2-54C Sur</div></div><div class='contenidos'>"
						+ "<div class='columna title'>Ultima Configuracion</div>"
						+ "<div class='columna'><a href='#'>Descargar</a></div></div><div class='contenidos'><div class='columna title'>Canal Principal</div><div class='columna'>Si</div></div>"
						+ "<div class='contenidos'><div class='columna title'>Canal BackUp</div><div class='columna'>Si</div></div></div></div>",
				marker : null
			}, {
				title : 'Chapinero',
				position : {
					lat : 4.648698,
					lng : -74.030198
				},
				content : "<h2>Chapinero</h2>",
				marker : null
			}, {
				title : 'Suba',
				position : {
					lat : 4.7614365,
					lng : -74.0829925
				},
				content : "<h2>Suba</h2>",
				marker : null
			}, {
				title : 'Chia',
				position : {
					lat : 4.867658,
					lng : -74.03792
				},
				content : "<h2>Chia</h2>",
				marker : null
			} ];

	addNewMarkers(markers, map);

	map.data.addListener('mouseover', function(event) {
		var nameDepartament = event.feature.getProperty('NOMBRE_DPT');
		map.data.overrideStyle(event.feature, {
			fillOpacity : 0.1
		});
	});

	map.data.addListener('click', function(event) {
		var nameDepartament = event.feature.getProperty('NOMBRE_DPT');
		map.data.overrideStyle(event.feature, {
			fillOpacity : 0.1
		});
	});

	map.data.addListener('mouseout', function(event) {
		map.data.revertStyle();
		map.data.overrideStyle(event.feature, {
			fillOpacity : 0.2
		});
	});

}

function addNewMarkers(markers, map) {

	var markersAmnt = markers.length;
	for (var i = 0; i < markersAmnt; i++) {

		var markerPos = new google.maps.LatLng(markers[i].position.lat,
				markers[i].position.lng);

		var marker = new google.maps.Marker({
			position : markerPos,
			map : map,
			title : markers[i].title,
		});

		var infoWindow = new google.maps.InfoWindow({
			content : markers[i].content
		});

		google.maps.event.addListener(marker, 'click',
				function(pointer, bubble) {
					return function() {
						bubble.open(map, pointer);
					};
				}(marker, infoWindow));
	}
}
