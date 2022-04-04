package pokemontypetester;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A Pokemon represented by its types.
 * 
 * @author Max Fagenson
 * @see {@link #PokemonTypes}
 */
public class Pokemon { //test

	private final PokemonTypes type1;
	private final PokemonTypes type2;
	private final List<PokemonTypes> extraImmunities;

	/**
	 * Constructs a Pokemon with 2 types. If {@code type1 == type2},
	 * {@code this.type2 == null}.
	 * 
	 * @param type1 - First type of the Pokemon
	 * @param type2 - Second type of the Pokemon
	 * @see {@link PokemonTypes}
	 */
	public Pokemon(PokemonTypes type1, PokemonTypes type2) {
		this(type1, type2, (PokemonTypes[]) null);
	}

	/**
	 * Constructs a Pokemon with 1 type. {@code this.type2 == null}.
	 * 
	 * @param type - Type of the Pokemon
	 * @see {@link PokemonTypes}
	 */
	public Pokemon(PokemonTypes type) {
		this(type, null);
	}

	/**
	 * Constructs a Pokemon with 2 types <strong>and</strong> extra immunities the
	 * Pokemon has, not strictly related to its typing. A Pokemon may be immune to
	 * different types due to in-game abilities such as Water Absorb, Sap Sipper,
	 * Levitate, and others.
	 * 
	 * @param type1           - First type of the Pokemon
	 * @param type2           - Second type of the Pokemon
	 * @param extraImmunities - Extra types which the Pokemon will be immune to. If
	 *                        the Pokemon should not be immune to any extra types,
	 *                        use {@link #Pokemon(PokemonTypes, PokemonTypes) this
	 *                        constructor} instead.
	 * @see {@link PokemonTypes}<br>
	 *      {@link #immunities()}
	 */
	public Pokemon(PokemonTypes type1, PokemonTypes type2, PokemonTypes... extraImmunities) {
		if (type1 == null && type2 == null)
			throw new IllegalArgumentException("Both type1 and type1 cannot be null");
		else if (type1 == null && type2 != null) {
			this.type1 = type2;
			this.type2 = null;
		} else if (type1 == type2) {
			this.type1 = Objects.requireNonNull(type1);
			this.type2 = null;
		} else {
			this.type1 = Objects.requireNonNull(type1);
			this.type2 = type2;
		}
		this.extraImmunities = extraImmunities != null ? Arrays.asList(extraImmunities) : null;
		if (this.extraImmunities != null)
			for (int i = 0; i < this.extraImmunities.size(); i++) {
				Objects.requireNonNull(this.extraImmunities.get(i),
						"Null pointer at index " + i + " of extraImmunities");
			}
	}

	/**
	 * Creates a Pokemon with a random set of types
	 * 
	 * @return Pokemon with random types. The Pokemon can either have 1 or 2 types.
	 * @see {@link PokemonTypes}
	 */
	public static Pokemon genMon() {
		var types = PokemonTypes.values();
		int i = new Random().nextInt((types.length) * 2);
		if (i >= types.length)
			return new Pokemon(types[i / 2]);
		int i2;
		do
			i2 = new Random().nextInt(types.length);
		while (i2 == i);
		return new Pokemon(types[i], types[i2]);
	}

	/**
	 * Calculates the damage multiplier of an incoming move of the given type
	 * against the Pokemon. If the Pokemon has 2 types, then the final Damage
	 * Multiplier is the product of both types' damage multipliers.
	 * 
	 * @param type - Type of move to check the multiplier against the Pokemon
	 * @return 0, .25, .5, 1, 2, or 4.
	 * @see {@link PokemonTypes#calcDmgMult(PokemonTypes)}
	 */
	public double calcDamageMult(PokemonTypes type) {
		Objects.requireNonNull(type);
		return type2 == null ? type1.calcDmgMult(type) : (type1.calcDmgMult(type) * type2.calcDmgMult(type));
	}

	/**
	 * Collects all weaknesses of the Pokemon, as well as their damage multipliers.
	 * 
	 * @return A map of PokemonType keys where their values are equivilent to
	 *         {@code calcDamageMult(key)}. If Pokemon has no weaknesses, then
	 *         {@code null} is returned.
	 * @see {@link #calcDamageMult()}
	 */
	public Map<PokemonTypes, Double> weaknesses() {
		final Map<PokemonTypes, Double> out = new HashMap<>();

		Arrays.asList(PokemonTypes.values()).stream().filter(t -> calcDamageMult(t) > 1)
				.forEach(t -> out.put(t, calcDamageMult(t)));
		return out.isEmpty() ? null : out;
	}

	/**
	 * Checks if Pokemon is weak to <strong>all</strong> given types.
	 * 
	 * @param types - Types to check if Pokemon is weak to
	 * @return {@code true} if the damage multiplier of all {@code types} >= 2,
	 *         otherwise {@code false}.
	 * @see {@link #calcDamageMult(PokemonTypes)}<br>
	 *      {@link #weaknesses()}
	 */
	public boolean isWeakToAll(PokemonTypes... types) {
		Objects.requireNonNull(types);
		for (int i = 0; i < types.length; i++)
			Objects.requireNonNull(types[i], "Null pointer at index " + i + " of inputted types");
		return Arrays.asList(types).stream().allMatch(t -> calcDamageMult(t) >= 2);
	}

	/**
	 * Collects all resistances of the Pokemon, as well as their damage multipliers.
	 * 
	 * @return A map of PokemonType keys where their values are equivilent to
	 *         {@code calcDamageMult(key)}. If Pokemon has no resistances, then
	 *         {@code null} is returned.
	 * @see {@link #calcDamageMult()}
	 */
	public Map<PokemonTypes, Double> resistances() {
		final Map<PokemonTypes, Double> out = new HashMap<>();
		Arrays.asList(PokemonTypes.values()).stream().filter(t -> calcDamageMult(t) > 0 && calcDamageMult(t) < 1)
				.forEach(t -> out.put(t, calcDamageMult(t)));
		return out.isEmpty() ? null : out;
	}

	/**
	 * Collects all immunities of the Pokemon
	 * 
	 * @return A list of all types the Pokemon is immune to. If the Pokemon is not
	 *         immune to any types, {@code null} is returned.
	 */
	public List<PokemonTypes> immunities() {
		List<PokemonTypes> imm = Arrays.asList(PokemonTypes.values()).stream().filter(t -> calcDamageMult(t) == 0)
				.collect(Collectors.toList());

		Optional.ofNullable(extraImmunities).ifPresent(x -> x.forEach(imm::add));
		return imm.isEmpty() ? null : imm;
	}

	/**
	 * Checks if Pokemon is resistant <strong>or</strong> immune to
	 * <strong>all</strong> given types.
	 * 
	 * @param types - Types to check if Pokemon is resistant or immune to.
	 * @return {@code true} if the damage multiplier of all {@code types} <= .5,
	 *         otherwise {@code false}.
	 * @see {@link #calcDamageMult(PokemonTypes)}<br>
	 *      {@link #resistances()}<br>
	 *      {@link #immunities()}
	 */
	public boolean isResistantToAll(PokemonTypes... types) {
		Objects.requireNonNull(types);
		for (int i = 0; i < types.length; i++)
			Objects.requireNonNull(types[i], "Null pointer at index " + i + " of inputted types");
		return Arrays.asList(types).stream().allMatch(t -> calcDamageMult(t) <= .5);
	}

	/**
	 * Generates a list of all different Pokemon type combinations which would be
	 * resistant <strong>or</strong> immune to <strong>all</strong> given types.
	 * 
	 * @param types - Types that all returned Pokemon should be resistant or immune
	 *              to.
	 * @return - List of Pokemon resistant to all given types. This list does not
	 *         include hypothetical Pokemon which are immune to any extra types, as
	 *         defined in the
	 *         {@link #Pokemon(PokemonTypes, PokemonTypes, PokemonTypes...)
	 *         constructor}. Optional is empty if no type combination resists all
	 *         given types.
	 * @see {@link #isResistantToAll(PokemonTypes...)}<br>
	 *      {@link Optional}
	 */
	public static Optional<List<Pokemon>> genMonResToAll(PokemonTypes... types) {
		Objects.requireNonNull(types);
		for (int i = 0; i < types.length; i++)
			Objects.requireNonNull(types[i], "Null pointer at index " + i + " of inputted types");
		List<PokemonTypes> allTypes = Arrays.asList(PokemonTypes.values()).stream().filter(type -> !type.weakTo(types))
				.collect(Collectors.toList());

		return Optional.ofNullable(switch (allTypes.size()) {
		case 0 -> null;
		case 1 -> new Pokemon(allTypes.get(0)).isResistantToAll(types) ? List.of(new Pokemon(allTypes.get(0))) : null;
		case 2 -> new Pokemon(allTypes.get(0), allTypes.get(1)).isResistantToAll(types)
				? List.of(new Pokemon(allTypes.get(0), allTypes.get(1)))
				: null;
		default -> genMonResTo3OrMore(allTypes, types);
		});

	}

	/**
	 * Helper method for {@link #genMonResToAll(PokemonTypes...)}
	 * 
	 * @param allTypes - All types not weak to any type passed into {@code types}.
	 * @param types    - Types that all returned Pokemon should be resistant or
	 *                 immune to.
	 * @return List of Pokemon resistant to all given types. {@code null} if no type
	 *         combination resists all given types.
	 * @see {@link #genMonResToAll(PokemonTypes...)}<br>
	 *      {@link #isResistantToAll(PokemonTypes...)}
	 */
	private static List<Pokemon> genMonResTo3OrMore(List<PokemonTypes> allTypes, PokemonTypes... types) {
		Objects.requireNonNull(allTypes);
		for (int i = 0; i < allTypes.size(); i++)
			Objects.requireNonNull(allTypes.get(i), "Null pointer at index " + i + " of extraImmunities");
		Objects.requireNonNull(types);
		for (int i = 0; i < types.length; i++)
			Objects.requireNonNull(types[i], "Null pointer at index " + i + " of inputted types");
		List<Pokemon> outList = allTypes.stream().map(Pokemon::new).filter(x -> x.isResistantToAll(types))
				.collect(Collectors.toList());

		for (int s = allTypes.size(), i = 0; i < s; i++)
			for (int j = s - 1; j > i; j--) {
				var testMon = new Pokemon(allTypes.get(i), allTypes.get(j));
				if (testMon.isResistantToAll(types))
					outList.add(testMon);
			}

		return outList.isEmpty() ? null : outList;
	}

	@Override
	public String toString() {
		return type2 == null ? type1.toString() : String.format("%s & %s", type1.toString(), type2.toString());
	}
}
