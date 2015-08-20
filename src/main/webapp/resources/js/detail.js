function loadPolygon() {
   var urlJson = document.getElementById('form_detail:jsonURL').value;
   if (urlJson != null) {
      PF('map_detail').map.data.loadGeoJson(urlJson);
      PF('map_detail').map.data.setStyle({
         fillColor : 'red',
         strokeColor : 'red',
         strokeWeight : 1,
         fillOpacity : 0.2
      });
   }

}
