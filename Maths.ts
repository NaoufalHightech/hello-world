
class Mathametics{
    
    output : number;

    constructor(){
        console.log("welcome");    
    }

    add(first:number, second: number):number{
        this.output = first+second;
        return this.output;
    }
    sub(first:number, second: number):number{
        this.output = first-second;
        return this.output;
    }
    mul(first:number, second: number):number{
        this.output = first*second;
        return this.output;
    }
    div(first:number, second: number):number{
        this.output = first/second;
        return this.output;
    }


}

window.onload = () => {
    var firstInp = prompt("Enter first value");
    var secondInp = prompt("Enter second value");
    var option = prompt("Enter your option");
    var output : number;

    var mathObj = new Mathametics();

    
    if ( "add" == option.toLowerCase()){
        output = mathObj.add(parseInt(firstInp), parseInt(secondInp));
    } else if("sub" == option.toLowerCase()){
        output = mathObj.sub(parseInt(firstInp), parseInt(secondInp));
    } else if(option == "div"){
        output = mathObj.div(parseInt(firstInp), parseInt(secondInp));
    } else if(option == "mul"){
        output = mathObj.mul(parseInt(firstInp), parseInt(secondInp));
    }

    var span = document.createElement("span");
    span.style.color = "RED";
    span.innerText = output.toString();
    document.body.appendChild(span);



}


