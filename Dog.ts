
module mp {  // Opening of Module -- > This similar to packages in Java. 

export class Mamal {   // we need to export the class for it to be used from Module. 
    
    private readonly numberVal : number = 10; // It's similar to const keyword

    private privateMethod(): any{             // private access modifier will make this method accessible from only this class. 
        console.log("Log of Private Method in Mamal");
    }
    protected protectedMethod(): any{      // protected access modifier will make this method accessible from child class only using "this" keyword.  
        console.log("Log of Protected Method in Mamal");
    }
    public publicMethod(): any{     // public access modifier will make this method accessible from any class
        console.log("Log of Public Method in Mamal");
    }
    constructor(){              // This constructor is for constructing this class
        console.log("Mamal Constructor");
    }
    getName(): void {       // public is the default access. 
        console.log("I am Mammal");
    }
}

export class Animal extends Mamal{  // we need to export the class for it to be used from Module. 
    
    constructor() {                 // This constructor is for constructing this class
        super();                    // super() is required to avoid compilation error, but there is no position requirement as in Java. 
        console.log("Animal Constructor");
    }

    getLegs() : void {
        console.log("welcome in Animal");  
        this.protectedMethod();  // we can call Protected from Child clases only, using this keyword.  
    }
    
    
}
export class Dog extends Animal {
    constructor(){
        super();
        console.log("Dog constructor");
    }
    getLegs(): void{        // This is a overloaded method
        console.log("welcome in Dog");
    }
    getChracters(): string{
        return "Braking";
    }
}

}  // Closing of Module -- > This similar to packages in Java. 

var d : mp.Animal = new mp.Dog();  //Run Time Polymorphisam
d.getLegs();
//console.log(d.getChracters());   --> this line has been commented, because the object "d" has been typecasted to Animal. 
d.getName();

var a: mp.Animal = new mp.Animal();
a.getLegs();

var m: mp.Mamal = new mp.Mamal();
m.publicMethod();

/*
**********************************OUTPUT OF ABOVE PROGRAM*****************************

D:\Typescripts_Projects\Project1>tsc Dog.ts

D:\Typescripts_Projects\Project1>node Dog.js
Mamal Constructor
Animal Constructor
Dog constructor
welcome in Dog
I am Mammal
Mamal Constructor
Animal Constructor
welcome in Animal
Log of Protected Method in Mamal
Mamal Constructor
Log of Public Method in Mamal

**************************************************************

*/