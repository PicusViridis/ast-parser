package com.picusviridis.astparser.tokenizer;

public enum ConstantType implements IType
{
    PI(Math.PI, "pi"),
    E(Math.E, "e"),
    NAN(Double.NaN, "nan", "null"),
    ERROR(Double.NaN, "error");

    private Double   value;
    private String[] identifiers;

    ConstantType(Double value, String... identifiers)
    {
        this.value = value;
        this.identifiers = identifiers;
    }

    public Double getValue()
    {
        return this.value;
    }

    public static ConstantType get(String identifier)
    {
        if (identifier == null)
        {
            return ConstantType.ERROR;
        }

        for (ConstantType type : ConstantType.values())
        {
            for (String id : type.identifiers)
            {
                if (id.compareTo(identifier.toLowerCase()) == 0)
                {
                    return type;
                }
            }
        }

        return ConstantType.ERROR;
    }

    @Override
    public String getIdentifier()
    {
        return this.identifiers[0];
    }
}
