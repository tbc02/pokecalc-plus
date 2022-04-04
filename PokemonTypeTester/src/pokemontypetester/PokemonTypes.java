package pokemontypetester;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An enum of Pokemon types that have weaknesses, resistances, and immunities.
 * 
 * @author tbc02
 *
 */
public enum PokemonTypes {
	NORMAL(List.of("fighting"), null, List.of("ghost")),
	FIRE(List.of("water", "ground", "rock"), List.of("fire", "grass", "ice", "bug", "steel", "fairy")),
	WATER(List.of("electric", "grass"), List.of("fire", "water", "ice", "steel")),
	ELECTRIC(List.of("ground"), List.of("electric", "flying", "steel")),
	GRASS(List.of("fire", "ice", "poison", "flying"), List.of("water", "electric", "grass", "ground")),
	ICE(List.of("fire", "fighting", "rock", "steel"), List.of("ice")),
	FIGHTING(List.of("flying", "psychic", "fairy"), List.of("bug", "rock", "dark")),
	POISON(List.of("ground", "psychic"), List.of("grass", "fighting", "poison", "bug", "fairy")),
	GROUND(List.of("water", "grass", "ice"), List.of("poison", "rock"), List.of("electric")),
	FLYING(List.of("electric", "ice", "rock"), List.of("grass", "fighting", "bug"), List.of("ground")),
	PSYCHIC(List.of("bug", "ghost", "dark"), List.of("fighting", "psychic")),
	BUG(List.of("fire", "flying", "rock"), List.of("grass", "fighting", "ground")),
	ROCK(List.of("water", "grass", "fighting", "ground", "steel"), List.of("normal", "fire", "poison", "flying")),
	GHOST(List.of("ghost", "dark"), List.of("poison", "bug"), List.of("normal", "fighting")),
	DRAGON(List.of("ice", "dragon", "fairy"), List.of("fire", "water", "electric", "grass")),
	DARK(List.of("fighting", "bug", "fairy"), List.of("ghost", "dark"), List.of("psychic")),
	STEEL(List.of("fire", "fighting", "ground"),
			List.of("normal", "grass", "ice", "flying", "psychic", "bug", "rock", "dragon", "steel", "fairy"),
			List.of("poison")),
	FAIRY(List.of("poison", "steel"), List.of("fighting", "bug", "dark"), List.of("dragon"));

	private final List<String> weakTo, resistantTo, immuneTo;

	PokemonTypes(List<String> weakTo, List<String> resistantTo, List<String> immuneTo) {
		this.weakTo = weakTo;
		this.resistantTo = resistantTo;
		this.immuneTo = Optional.ofNullable(immuneTo).orElse(null);
	}

	PokemonTypes(List<String> weakTo, List<String> resistantTo) {
		this(weakTo, resistantTo, null);
	}

	/**
	 * Collects the Pokemon type's weaknesses.
	 * 
	 * @return List of weaknesses. Optional is empty if type has no weaknesses.
	 * @see {@link #weakTo(PokemonTypes)}<br>
	 *      {@link #immuneTo(PokemonTypes...)}
	 */
	public Optional<List<PokemonTypes>> weaknesses() {
		if (weakTo == null)
			return Optional.empty();
		List<PokemonTypes> out = new ArrayList<>();
		weakTo.forEach((x) -> out.add(fromString(x)));
		return Optional.of(out);
	}

	/**
	 * Checks if Pokemon type is weak to a specific type.
	 * 
	 * @param type - Type to check for.
	 * @return {@code true} if weak to, {@code false} otherwise.
	 * @see {@link #weaknesses()}<br>
	 *      {@link #weakTo(PokemonTypes...)}
	 */
	public boolean weakTo(PokemonTypes type) {
		return weakTo == null ? false : weakTo.contains(Objects.requireNonNull(type).toString().toLowerCase());
	}

	/**
	 * Checks if Pokemon type is weak to a set of types.
	 * 
	 * @param types - Types to check for.
	 * @return {@code true} if weak to <strong>all</strong>, {@code false}
	 *         otherwise.
	 * @see {@link #weaknesses()}<br>
	 *      {@link #weakTo(PokemonTypes)}
	 */
	public boolean weakTo(PokemonTypes... types) {
		return List.of(Objects.requireNonNull(types)).stream().allMatch(t -> t != null && weakTo(t));
	}

	/**
	 * Collects the Pokemon type's resistances.
	 * 
	 * @return List of resistances. Optional is empty if type has no weaknesses.
	 * @see {@link #resistantTo(PokemonTypes)}<br>
	 *      {@link #immuneTo(PokemonTypes...)}
	 */
	public Optional<List<PokemonTypes>> resistances() {
		if (resistantTo == null)
			return Optional.empty();
		List<PokemonTypes> out = new ArrayList<>();
		resistantTo.forEach((x) -> out.add(fromString(x)));
		return Optional.of(out);
	}

	/**
	 * Checks if Pokemon type is resistant to a specific type.
	 * 
	 * @param type - Type to check for.
	 * @return {@code true} if resistant of, {@code false} otherwise.
	 * @see {@link #resistances()}<br>
	 *      {@link #resistantTo(PokemonTypes...)}
	 */
	public boolean resistantTo(PokemonTypes type) {
		return resistantTo == null ? false : resistantTo.contains(Objects.requireNonNull(type).toString().toLowerCase());
	}

	/**
	 * Checks if Pokemon type is resistant to a set of types.
	 * 
	 * @param types - Types to check for.
	 * @return {@code true} if resistant of <strong>all</strong>, {@code false}
	 *         otherwise.
	 * @see {@link #resistances()}<br>
	 *      {@link #resistantTo(PokemonTypes)}
	 */
	public boolean resistantTo(PokemonTypes... types) {
		return List.of(Objects.requireNonNull(types)).stream().allMatch(t -> t != null && resistantTo(t));
	}

	/**
	 * Collects the Pokemon type's immunites.
	 * 
	 * @return List of immunities. Optional is empty if type has no immunites.
	 * @see {@link #immuneTo(PokemonTypes)}<br>
	 *      {@link #immuneTo(PokemonTypes...)}
	 */
	public Optional<List<PokemonTypes>> immunities() {
		if (immuneTo == null)
			return Optional.empty();
		List<PokemonTypes> out = new ArrayList<>();
		immuneTo.forEach((x) -> out.add(fromString(x)));
		return Optional.of(out);
	}

	/**
	 * Checks if Pokemon type is immune to a specific type.
	 * 
	 * @param type - Type to check for.
	 * @return {@code true} if immune to, {@code false} otherwise.
	 * @see {@link #immunities()}<br>
	 *      {@link #immuneTo(PokemonTypes...)}
	 */
	public boolean immuneTo(PokemonTypes type) {
		return immuneTo == null ? false : immuneTo.contains(Objects.requireNonNull(type).toString().toLowerCase());
	}

	/**
	 * Checks if Pokemon type is immune to a set of types.
	 * 
	 * @param types - Types to check for.
	 * @return {@code true} if immune to <strong>all</strong>, {@code false}
	 *         otherwise.
	 * @see {@link #immunities()}<br>
	 *      {@link #immuneTo(PokemonTypes)}
	 */
	public boolean immuneTo(PokemonTypes... types) {
		return List.of(Objects.requireNonNull(types)).stream().allMatch(t -> t != null && immuneTo(t));
	}

	/**
	 * Returns the damage multiplier of a given Pokemon Type against this enum
	 * constant.
	 * 
	 * @param type - Type to test the damage multiplier of.
	 * @return 0 if immune, .5 if resistant, 2 if weak, otherwise 1.
	 * @see {@link #weaknesses()}<br>
	 *      {@link #resistances()}<br>
	 *      {@link #immunities()}
	 */
	public double calcDmgMult(PokemonTypes type) {
		if (immuneTo != null && immuneTo.contains(Objects.requireNonNull(type).toString().toLowerCase()))
			return 0;
		if (resistantTo != null && resistantTo.contains(Objects.requireNonNull(type).toString().toLowerCase()))
			return .5;
		if (weakTo != null && weakTo.contains(Objects.requireNonNull(type).toString().toLowerCase()))
			return 2;
		return 1;
	}

	/**
	 * Converts a String representation of a Pokemon type into an enum constant.
	 * Useful for collecting user input.
	 * 
	 * @param type - String representation of a Pokemon type.
	 * @return Enum constant of {@code type}.
	 */
	public static PokemonTypes fromString(String type) {
		try {
			return List.of(values()).stream().filter(t -> t.toString().equalsIgnoreCase(Objects.requireNonNull(type)))
					.collect(Collectors.toList()).get(0);
		} catch (Throwable e) {
			throw new IllegalArgumentException("Invalid type: " + type);
		}

	}

	@Override
	public String toString() {
		return String.format("%c%s", Character.toUpperCase(name().charAt(0)), name().substring(1).toLowerCase());
	}
}
