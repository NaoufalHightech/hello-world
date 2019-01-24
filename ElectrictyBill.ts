module mp{


export class Test{
    public static get(): string{   // static method
        return "test";
    }
}

export interface Interclass{
    year : number;
    age() : void;
}

export class InterTest implements Interclass{
    year = 20;
    age():void {
        console.log("Your age is very good "+this.year);
    }
}

export abstract class Plan {

    rate : number;

    constructor() {
        console.log("Abstract Plan Constructor called.");
    }
    abstract getRate(): number;

    calculateBill(units: number): number {
        return units * this.rate;
    }

}

export class DomesticPlan extends Plan {

    getRate(): number {
        this.rate = 3.5;
        return 3.5;
    }

}

export class CommercialPlan extends Plan {

    getRate(): number {
        this.rate = 4.5;
        return 4.5;
    }

}

export class InstitutionalPlan extends Plan {

    getRate(): number {
        this.rate = 7.5;
        return 7.5;
    }

}

} // end of module

class PlanFactory {

    getPlanObject(planName: string): mp.Plan {

        if (planName == "Domestic") {
            return new mp.DomesticPlan();
        } else if (planName == "Commercial") {
            return new mp.CommercialPlan();
        } else if (planName == "Institutional") {
            return new mp.InstitutionalPlan();
        }
    }
}

var planObj: PlanFactory = new PlanFactory();

var domObj: mp.DomesticPlan = planObj.getPlanObject("Domestic");

console.log("Rate "+domObj.getRate());

console.log("Callate "+domObj.calculateBill(200));

var test : mp.Test = mp.Test.get();

var ageTest : mp.Interclass = new mp.InterTest();
//ageTest.year = 34;
ageTest.age();




