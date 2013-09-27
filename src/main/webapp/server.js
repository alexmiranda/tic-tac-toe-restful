function Server(){
	
	var _rootURL = "http://localhost:8080/tic-tac-toe-restful/api/games",
		_game;
	
	return {
		newGame: function(callback){
			_createGame(callback);
		},
		
		play: function(position, mark, callback){
			_play(_game, position, mark, callback);
		},
		
		currentGame: function(){
			return _game;
		},
		
		refreshGame: function(callback){
			_refreshGame(_game.getGameId(), callback);
		},
		
		subscribe: function(gameId, callback){
			_subscribe(gameId, callback);
		}
	};

	function _play(game, position, mark, callback) {
	    console.log('playing...');
	    $.ajax({
	        type: 'POST',
	        contentType: 'application/json',
	        url: _rootURL+"/"+game.getGameId(),
	        dataType: "json",
	        data: convertPlaytoJSON(position, mark),
	        statusCode: {
	        	202:function(jqXHR, data) { callback(true); },
            },
	    });
	}
	
	function _refreshGame(gameId, callback) {
	    console.log('refreshing game...');
	    $.ajax({
	        type: 'GET',
	        contentType: 'application/json',
	        url: _rootURL+"/"+gameId,
	        dataType: "json",
	        statusCode: {
	        	200:function(jqXHR, textStatus, data) { callback(data.responseJSON); },
            },
	    });
	}
	
	function convertPlaytoJSON(position, mark) {
	    return JSON.stringify({
	        "position": position,
	        "mark": mark,
	        });
	}
	
	function _createGame(callback) {
	    console.log('creating game...');
	    $.ajax({
	        type: 'POST',
	        contentType: 'application/json',
	        url: _rootURL,
	        dataType: "json",
	        data: "{}",
	        statusCode: {
	        	200:function() { alert("200"); },
	        	201:function(jqXHR) { 
	        		initGame(jqXHR.getResponseHeader('Location'));
	        		callback(_game.getGameId());
	        	}
            },
	    });
	   
	}
	
	function _subscribe(gameId, callback) {
	    console.log('refreshing game...');
	    $.ajax({
	        type: 'GET',
	        contentType: 'application/json',
	        url: _rootURL+"/"+gameId,
	        dataType: "json",
	        statusCode: {
	        	200:function(jqXHR, textStatus, data) { 
	        			initGame(data.responseJSON.links[0].href);
	        			callback(true);
	        		},
            },
	    });
	}

	function initGame(location){
		_game = new Game(location);
	}

}

