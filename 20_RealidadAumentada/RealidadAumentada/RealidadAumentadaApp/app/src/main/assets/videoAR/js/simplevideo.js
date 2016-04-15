var World = {
	loaded: false,

	init: function initFn() {
		this.createOverlays();
	},

	createOverlays: function createOverlaysFn() {
		this.tracker = new AR.ClientTracker("assets/targetcollections.wtc", {
			onLoaded: this.worldLoaded
		});

		var video = new AR.VideoDrawable("assets/video.mp4", 1.0, {
			offsetY: 0.2,
			offsetX: 0.2,
		});

		var pageOne = new AR.Trackable2DObject(this.tracker, "*", {
			drawables: {
				cam: [video]
			},

			onEnterFieldOfVision: function onEnterFieldOfVisionFn() {
				video.play(-1);
			}
		});
	},

	worldLoaded: function worldLoadedFn() {
		setTimeout(function() {
			var e = document.getElementById('loadingMessage');
			e.parentElement.removeChild(e);
		}, 2500);
	}
};

World.init();
