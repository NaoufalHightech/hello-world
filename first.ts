var i : number = 10;

console.log(i);

var j : string = "Hello";

console.log(j);

var k : boolean = true;

console.log(k);

function addition1(first:number, second:number) : string{
    return "Output "+(first+second);
}

console.log(addition1(10,20));

console.log(addition1(10.232,20.7898));


//document.body.innerHTML = addition1(10.5,20.3);



class Test{
    constructor(first: any, second:string, third:boolean){
        console.log("Inside Constructor!!! "+first+" "+second+" "+third);
    }
    move() : string {
        return "move method world";
    }
}

var obj = new Test(1234,"Arun",true);

var output = obj.move();

console.log(output);

class Test2{
    constructor(first:string)
    constructor(first:string, second:boolean);
    constructor(first:string, second:boolean, third:boolean);
    constructor(first:string, second ? :boolean, third ? :boolean){
        console.log("Welcome second "+first+" second "+second+" third "+third);
    }
}

var obj1 = new Test2("Hello");
var obj1 = new Test2("Hello",true);



function check(){
    let i = 10;
    console.log(i);
    if(true){
        let i = 20;
        console.log(i);
    }
    console.log(i);
}

check();













