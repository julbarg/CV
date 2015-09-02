var map;

function initialize() {
	var mapCanvas = document.getElementById('map-canvas');
	var mapOptions = {
		center : new google.maps.LatLng(4.1156602, -72.953885),
		zoom : 5,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		streetViewControl : false,
		disableDefaultUI : true,
		minZoom : 5
	}
	map = new google.maps.Map(mapCanvas, mapOptions);

	var markers = [ {
		title : 'Bosa',
		position : {
			lat : 4.6256415,
			lng : -74.1876155
		},
		content : "<h2>Bosa</h2>",
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
