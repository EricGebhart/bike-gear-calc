# bike-gear-calc

A Clojure(script) library designed to do bicycle gear calculations
for fixed-gear, internal hubs and cassettes.

There are calculations for gear-inches, meters of development, and gain-ratios
For fixed gear riders there is also the number of skid patches 
and gears within 2% of a gear. And finally, given a gear, the speed at a
given cadence/RPMs.

## Current state

Compiles for both clojure and clojurescript. 

### Tests

At this point tests are correct, but float comparisons are not always.

### API

The API is settling on maps at the moment.  Fill in an *core/any-bike* map
then call *bike* in one of the three bike namespaces. 
_(fixie, hub-gear or deraileur-gear)._

There are specs for the input bike maps. The way to use this is
to create an any-bike and fill it in how you like. Then coerce it
to a :fixie :internal or :deraileur bike.

Then hand it off to get the data.

`(calc-gears (coerce-bike :fixie <your-any-bike>)`

Will give you everything it can for a fixie.

### Data etc.

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

## fixed gear skid patches

I did not write this, it came from the bike forums somewhere I think.

Simplify the gear ratio to the smallest equivalent whole number ratio.
For single legged skidders, the number of skid patches is the denominator.
For folks who can skid with either foot forward (ambidextrous skidders):
if the numerator is even, the number of skid patches is still the denominator.
if the numerator is odd, the number of skid patches is the denominator * 2.

48x20 => 24x10 => 12x5 results in 5 skid patches for both single and ambidextrous skidders.
43x18 results in 18 patches for single skidders but 36 for ambidextrous skidders.

Notice that the number of skid patches doubles not when the chainring is
odd, but when the simplified gear numerator is odd, thus ... 42x16 =>
21x8 results in 8 patches for single skidders but 16 for ambidextrous
skidders. What gear should I choose? Non-skidders should choose even
toothed gears to maximize chain and sprocket life. Single-legged
skidders should choose odd toothed chainrings to maximize skid
patches. Ambidextrous skidders can choose even toothed gears where the
chainring simplifies to an odd number to get both extended drivetrain
wear and doubled skid patches.


## References

None of the ideas behind this are new.  The data for wheel sizes,
freewheels and the internal gear hub ratios came from Sheldon Brown's
gear calculator site at Harris Cyclery. The calculations for skid
patches is also well known, mostly being discussed on the bike forums.
Gain Ratios were Sheldon's idea. It has been many years now, and I don't
know that anyone uses them. But I made a function for them
in case anyone likes them.

In sheldon's words.  Gain ratio is calculated like this.

Divide the wheel radius in mm, by the crank length in mm. this will
yield a single radius ratio applicable to all of the gears of a given
bike. The individual gear ratios are calculated as with gear inches,
using this radius ratio instead of the wheel size.


## To do.

 * Deploy to clojars
 * Fix floating point comparisons in the tests
 * Add clojure specs for the data.  -- part way done.
 * An old fashioned, printable gear shifting chart ?
 * Perhaps a CLI ?
 * defrecords ?  maps and namespaces are working ok at the moment.
 * better wheel sizes.

## Usage

There are currently two good ways to use this. You can pass the appropriate
parameters to *bike* in one of the 3 bike namespaces 
_fixie, hub-gear, or deraileur-gear_. Or you can get an *(any-bike)* map from
core and fill it in as you wish, then give that to one of the *bike* 
functions. This is makes it easy to have a bike definition that could be
a hub-bike, a fixed gear or a deraileur gear bike and create one of the three
from it. 

The *data* namespace has _wheel-sizes_, _internal-hubs_, _sprocket-clusters_,
and _crank-lengths_ to aide with the choices. 

*With spec, you should be able to get these with `(doc ::bike-gear-calc/fixie)`.*

 _bike-gear-calc.fixie/bike_
A new fixie wants these. 
 * ring       - The chainring size.
 * sprocket   - The sprocket size
 * wheel-dia  - The wheel diameter in mm. default 670 = 25x700c.
 * crank-len  - The crank length in mm. default 170

 _bike-gear-calc.hub-gear/bike_
A new hub-gear wants all that plus. 
 * internal-ratios - a vector of ratios

_bike-gear-calc.deraileur-gear/bike_ 
A deraileur-gear bike wants these. 
 * rings       - A vector of chainrings
 * sprockets   - A vector of sprockets
 * wheel-dia   - The wheel diameter in mm.
 * crank-len   - The crank length in mm.
 
Your new bike will have a variety of data depending upon the bike.
hub-gear and deraileur gear bikes get a list of gear-maps which have
_:gear-inches_, _:meters-dev_, and _:gain-ratio_ along with the _:ratio_
or _:sprocket_.  Additionally there is also _:speeds_ which is a list of
speeds at rpm. Currently speeds are kmh due to a lack of plumbing.

Deraileur bikes divide their gear vectors by ring size. For example, 
there will be a vector of 3 maps of gears if you have 3 rings.
 
Fixed gear bikes have all of that plus _:close-gears_ and _:skid-patches_. 
Close gears is a list of bike maps with gears which are within 2% of the gear
ratio of the current bike.

Skid patches is a vector of two numbers. The first is single footed skid
patches and the second is ambidextrous skid patches.
 
 
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
