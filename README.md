# bike-gear-calc

A Clojure(script) library designed to do bicycle gear calculations
for fixed-gear, internal hubs and cassettes.

There are calculations for gear-inches, meters of development, and gain-ratios
For fixed gear riders there is also the number of skid patches 
and gears within 2% of a gear. And finally, given a gear, the speed at a
given cadence/RPMs.

At this point tests are correct, but float comparisons are not always.

The API is still in a bit of flux, but settling. I'm not quite happy with it.

The data namespace has maps for wheel size, crank lengths, standard
cassette/freewheel configurations and the internal ratios for a number
of internally geared hubs

Wheel sizes used in the calculations are the diameter in millimeters. 
They are where any errors can be introduced, it might be best to
just measure yours instead of using the wheel sizes from data. 
But then again an error of few millimeters isn't going to change things 
that much.

The original idea was that this library provides everything needed to create 
a nice gear calculator SPA in clojurescript. But then, well, it's so simple,
it would be nice if it worked in clojure, and then it would be nice to
use clojure.spec, so here we are, on the way.

## References

None of the ideas behind this are new.  The data for wheel sizes,
freewheels and the internal gear hub ratios came from Sheldon Brown's
gear calculator site at Harris Cyclery. The calculations for skid
patches is also well known, mostly being discussed on the bike forums.
Gain Ratios were Sheldon's idea. It has been many years now, and I don't
know that anyone uses them. But I made a function for them
in case anyone likes them.

## To do.

* Fix floating point comparisons in the tests
* Add clojure specs for the data.  
* Add directives so it will work in both clojure and clojurescript
* An old fashioned, printable gear shifting chart ?
* Perhaps a CLI ?

## Usage

Well, ok. I'll get to it.  In the mean time look at the tests.

## License

Copyright © 2019 Eric Gebhart

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
