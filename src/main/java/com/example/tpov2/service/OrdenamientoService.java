package com.example.tpov2.service;

import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenamientoService {

    private static final Logger log = LoggerFactory.getLogger(OrdenamientoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> ordenarConQuicksort(String criterio) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("[Quicksort] Ordenando {} usuarios por '{}'.", usuarios.size(), criterio);
        quickSort(usuarios, 0, usuarios.size() - 1, getComparador(criterio));
        log.info("[Quicksort] Ordenamiento completado.");
        return usuarios;
    }

    public List<Usuario> ordenarConMergesort(String criterio) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("[Mergesort] Ordenando {} usuarios por '{}'.", usuarios.size(), criterio);
        mergeSort(usuarios, getComparador(criterio));
        log.info("[Mergesort] Ordenamiento completado.");
        return usuarios;
    }

    private Comparator<Usuario> getComparador(String criterio) {
        switch (criterio.toLowerCase()) {
            case "nombre":
                return Comparator.comparing(Usuario::getNombre, Comparator.nullsLast(String::compareTo));
            case "edad":
                return Comparator.comparing(Usuario::getEdad, Comparator.nullsLast(Integer::compareTo));
            default:
                throw new IllegalArgumentException("Criterio no soportado: " + criterio);
        }
    }

    // ==================================================================
    // Implementación de Quicksort con Logging
    // ==================================================================
    private void quickSort(List<Usuario> list, int begin, int end, Comparator<Usuario> comparator) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end, comparator);
            log.debug("[Quicksort] Partición en índice {}. Sub-lista izquierda: [{} a {}], Sub-lista derecha: [{} a {}]",
                    partitionIndex, begin, partitionIndex - 1, partitionIndex + 1, end);
            quickSort(list, begin, partitionIndex - 1, comparator);
            quickSort(list, partitionIndex + 1, end, comparator);
        }
    }

    private int partition(List<Usuario> list, int begin, int end, Comparator<Usuario> comparator) {
        Usuario pivot = list.get(end);
        log.debug("[Quicksort] Pivote elegido: {} (en índice {})", pivot.getNombre(), end);
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
                log.debug("[Quicksort] Intercambio: {} <-> {}", list.get(j).getNombre(), list.get(i).getNombre());
            }
        }
        swap(list, i + 1, end);
        log.debug("[Quicksort] Pivote movido a la posición final de partición: {}", i + 1);
        return i + 1;
    }

    private void swap(List<Usuario> list, int i, int j) {
        Usuario temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // ==================================================================
    // Implementación de Mergesort con Logging
    // ==================================================================
    private void mergeSort(List<Usuario> list, Comparator<Usuario> comparator) {
        if (list.size() <= 1) {
            return;
        }

        int mid = list.size() / 2;
        List<Usuario> left = new ArrayList<>(list.subList(0, mid));
        List<Usuario> right = new ArrayList<>(list.subList(mid, list.size()));

        log.debug("[Mergesort] Dividiendo: Izquierda -> {}, Derecha -> {}",
                getNombres(left), getNombres(right));

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        merge(list, left, right, comparator);
    }

    private void merge(List<Usuario> list, List<Usuario> left, List<Usuario> right, Comparator<Usuario> comparator) {
        log.debug("[Mergesort] Fusionando: Izquierda -> {}, Derecha -> {}",
                getNombres(left), getNombres(right));
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
        log.debug("[Mergesort] Resultado de la fusión -> {}", getNombres(list));
    }

    private List<String> getNombres(List<Usuario> usuarios) {
        return usuarios.stream().map(Usuario::getNombre).collect(Collectors.toList());
    }
}
