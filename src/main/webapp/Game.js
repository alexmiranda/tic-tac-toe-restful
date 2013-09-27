function Game(location){
	var _location = location,
		_id;
	
	_id = _location.substring(_location.lastIndexOf('/')+1, _location.length);
	  
	  return {
	        getLocation: function () {
	            return _location;
	        },
	        
	        getGameId: function(){
	        	return _id;
	        },
	        
	  };
	  
}