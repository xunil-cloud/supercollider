

+Routine {
	embed { arg inval;
		func.value(inval)
	}

}

+Task {
	embed { arg inval;
		originalStream.embed(inval)
	}
	refresh {
		stream = originalStream
	}
	isPlaying {
		^stream != nil
	}
}

+Pattern {
	
	loop { ^this.asStream.loop  }

	repeat { arg repeats = inf;
		^this.asStream.repeat(repeats)
	}

}

+Stream {
	loop { 
		^Routine.new({ arg inevent;
			var res;
			inf.do({
				res = this.next(inevent);
				if(res.isNil, { res = this.reset.next(inevent) });
				inevent = res.yield(inevent)
			})
		});  
	}
	
	repeat { arg repeats = inf;
		
		^Routine.new({ arg inevent;
			var res;
			inf.do({
				res = this.next(inevent);
				if(res.isNil, { 
					res = this.reset.next(inevent);
					repeats = repeats - 1;  
				});
				if(repeats > 0, {
					inevent = res.yield(inevent)
				}, {
					nil.alwaysYield 
				});
			})
		});
	}
}

+Object {
	loop {}
	repeat { arg repeats = inf;
		^Routine.new({ repeats.do({ arg inval; inval = this.yield(inval) }) })
	}
}

+Nil {
	repeat {}
}


+UGen {

	lag { arg lagTime=0.1;
		^Lag.multiNew(this.rate, this, lagTime)
	}
	lag2 { arg lagTime=0.1;
		^Lag2.multiNew(this.rate, this, lagTime)
	}
	lag3 { arg lagTime=0.1;
		^Lag3.multiNew(this.rate, this, lagTime)
	}

}


