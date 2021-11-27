package hamburgueria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Result {

	public static Lanche x_burger = new Lanche("x-burguer",
			new ArrayList<>(Arrays.asList("pao", "queijo", "hamburguer", "maionese")));

	public static Lanche x_salada = new Lanche("x-salada",
			new ArrayList<>(Arrays.asList("pao", "queijo", "hamburguer", "tomate", "cebola", "maionese")));

	public static Lanche x_bacon = new Lanche("x-bacon",
			new ArrayList<>(Arrays.asList("pao", "queijo", "hamburguer", "bacon", "maionese")));

	public static Lanche x_tudo = new Lanche("x-tudo",
			new ArrayList<>(Arrays.asList("pao", "queijo", "bacon", "tomate", "cebola", "maionese")));

	public static List<Lanche> lanches = Arrays.asList(x_burger, x_salada, x_bacon, x_tudo);

	public static List<String> avulsos = Arrays.asList("bacon", "tomate", "cebola", "picles");

	public static String ingredientes(String pedido) {

		String[] pedidoMod = pedido.replace(" ", "").split(",");

		if (pedidoMod.length == 1) {
			Optional<Lanche> lanche = lanches.stream().filter(l -> l.nome.equalsIgnoreCase(pedidoMod[0].toLowerCase()))
					.findFirst();
			if (lanche.isPresent()) {
				lanche.get().ingredientes.sort(Comparator.naturalOrder());
				return lanche.get().ingredientes.stream().collect(Collectors.joining(","));
			} else
				return "invalido";
		}

		for (Lanche lanche : lanches) {
			if (pedidoMod[0].equalsIgnoreCase(lanche.nome.toLowerCase())) {
				List<String> addi = new ArrayList<>();
				List<String> rem = new ArrayList<>();
				for (int i = 1; i < pedidoMod.length; i++) {
					if (pedidoMod[i].startsWith("+")) {
						String aux = pedidoMod[i].replace("+", "");
						aux = aux.toLowerCase();
						addi.add(aux);
					} else {
						String aux2 = pedidoMod[i].replace("-", "");
						aux2 = aux2.toLowerCase();
						rem.add(aux2);
					}
				}
				boolean contemTodosIngredientesAdd = avulsos.containsAll(addi);
				boolean contemTodosIngredientesRem = lanche.ingredientes.containsAll(rem);
				if (contemTodosIngredientesAdd && contemTodosIngredientesRem) {
					lanche.ingredientes.removeIf(i -> rem.contains(i));
					List<String> addNovos = addi.stream().filter(i -> !lanche.ingredientes.contains(i.toLowerCase()))
							.collect(Collectors.toList());

					lanche.ingredientes.addAll(addNovos);

					lanche.ingredientes.sort(Comparator.naturalOrder());

					return lanche.ingredientes.stream().collect(Collectors.joining(","));
				} else {
					return "invalido";
				}
			} else {
				return "invalido";
			}
		}
		return "";
	}

	public static class Lanche {

		public String nome;
		public List<String> ingredientes;

		public Lanche(String nome, List<String> ingredientes) {
			this.nome = nome;
			this.ingredientes = ingredientes;
		}

	}
}