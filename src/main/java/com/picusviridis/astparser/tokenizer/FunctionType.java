package com.picusviridis.astparser.tokenizer;

import com.picusviridis.astparser.executioner.AbstractExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.AddExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.DivExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.ModExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.MultExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.PowExecutioner;
import com.picusviridis.astparser.executioner.binary.algebra.SubExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.AndExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.EqExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.InfEqExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.InfExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.NotEqExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.OrExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.SupEqExecutioner;
import com.picusviridis.astparser.executioner.binary.bool.SupExecutioner;
import com.picusviridis.astparser.executioner.nary.MaxExecutioner;
import com.picusviridis.astparser.executioner.nary.MinExecutioner;
import com.picusviridis.astparser.executioner.ternary.TernaryExecutioner;
import com.picusviridis.astparser.executioner.unary.algebra.AbsExecutioner;
import com.picusviridis.astparser.executioner.unary.algebra.CeilExecutioner;
import com.picusviridis.astparser.executioner.unary.algebra.FloorExecutioner;
import com.picusviridis.astparser.executioner.unary.algebra.RoundExecutioner;
import com.picusviridis.astparser.executioner.unary.algebra.SqrtExecutioner;
import com.picusviridis.astparser.executioner.unary.bool.NotExecutioner;
import com.picusviridis.astparser.executioner.unary.logarithmic.ExpExecutioner;
import com.picusviridis.astparser.executioner.unary.logarithmic.LnExecutioner;
import com.picusviridis.astparser.executioner.unary.logarithmic.LogExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.AcosExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.AsinExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.AtanExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.CosExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.SinExecutioner;
import com.picusviridis.astparser.executioner.unary.trigonometry.TanExecutioner;

public enum FunctionType implements IType
{
    ABS(new AbsExecutioner(), "abs"),
    MAX(new MaxExecutioner(), "max"),
    MIN(new MinExecutioner(), "min"),
    ROUND(new RoundExecutioner(), "round"),
    SQRT(new SqrtExecutioner(), "sqrt"),
    CEIL(new CeilExecutioner(), "ceil"),
    FLOOR(new FloorExecutioner(), "floor"),
    LOG(new LogExecutioner(), "log"),
    LN(new LnExecutioner(), "ln"),
    EXP(new ExpExecutioner(), "exp", "e"),
    COS(new CosExecutioner(), "cos"),
    SIN(new SinExecutioner(), "sin"),
    TAN(new TanExecutioner(), "tan"),
    ACOS(new AcosExecutioner(), "acos"),
    ASIN(new AsinExecutioner(), "asin"),
    ATAN(new AtanExecutioner(), "atan"),
    PLUS(new AddExecutioner(), "plus"),
    MINUS(new SubExecutioner(), "minus"),
    MULT(new MultExecutioner(), "mult"),
    DIV(new DivExecutioner(), "div"),
    POW(new PowExecutioner(), "pow"),
    MOD(new ModExecutioner(), "mod"),
    OR(new OrExecutioner(), "or"),
    AND(new AndExecutioner(), "and"),
    SUP(new SupExecutioner(), "sup"),
    SUP_EQ(new SupEqExecutioner(), "supeq"),
    INF(new InfExecutioner(), "inf"),
    INF_EQ(new InfEqExecutioner(), "infeq"),
    EQ(new EqExecutioner(), "eq"),
    NOT(new NotExecutioner(), "not"),
    NOT_EQ(new NotEqExecutioner(), "noteq"),
    TERNARY(new TernaryExecutioner(), "ternary"),
    ERROR;

    private AbstractExecutioner executioner;
    private String[]            identifiers;

    FunctionType()
    {
        this.identifiers = new String[0];
    }

    FunctionType(AbstractExecutioner executioner, String... identifiers)
    {
        this.executioner = executioner;
        this.executioner.setType(this);
        this.identifiers = identifiers;
    }

    public AbstractExecutioner getExecutioner()
    {
        return this.executioner;
    }

    public static FunctionType get(String identifier)
    {
        if (identifier == null)
        {
            return FunctionType.ERROR;
        }

        for (FunctionType type : FunctionType.values())
        {
            for (String id : type.identifiers)
            {
                if (id.compareTo(identifier.toLowerCase()) == 0)
                {
                    return type;
                }
            }
        }

        return FunctionType.ERROR;
    }

    @Override
    public String getIdentifier()
    {
        return this.identifiers[0];
    }
}
