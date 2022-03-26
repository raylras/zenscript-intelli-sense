import crafttweaker.events.IEventManager as em;

function add(a as int, b as int) as int {
    return a + b;
}

print(add(1,1));

zenClass Foo{
    val bar as int = 1;

    zenConstructor(){}

    function baz(a as float, b as float) as double {
        return 0.9f + 0.1f;
    }
}

var item = <minecraft:grass>;

var map = {"1" : 1, "2" : 2};
for a,b in map {
    print(a);
}

for i in 0 .. 1 {

}

a.b(x)[2].c[d](f).g;
