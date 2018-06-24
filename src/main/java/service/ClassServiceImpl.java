package service;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ClassServiceImpl implements ClassService{

    private static final ClassService classService = new ClassServiceImpl();
    private static final Map<Class<?>, Integer> classMap = new HashMap<>();
    private static final Map<Class<?>, Integer> interfaceMap = new HashMap<>();

    private ClassServiceImpl() {
    }

    public static ClassService getInstance() {
        return classService;
    }

    public List<Class<?>> sort(List<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) return Collections.emptyList();

        return classes.stream()
                .sorted(Comparator.comparingInt(this::getDepth))
                .collect(Collectors.toList());
    }

    private int getClassDepth(Class<?> clazz){
        if (Object.class.equals(clazz)) return 0;
        Integer prevIndex = classMap.get(clazz);
        if (prevIndex == null) {
            int superIndex = getClassDepth(clazz.getSuperclass()) + 1;
            classMap.put(clazz, superIndex);
            return superIndex;
        }
        return prevIndex;
    }

    private int getInterfaceDepth(Class<?> clazz){
        if (clazz == null) return 0;
        Integer prevIndex = interfaceMap.get(clazz);
        int index = 0;
        if (prevIndex == null) {
            for (Class<?> e : clazz.getInterfaces()) {
                int superIndex = getInterfaceDepth(e) + 1;
                index = Math.max(superIndex, index);
            }
            interfaceMap.put(clazz, index);
            return index;
        }
        return prevIndex;
    }

    private int getDepth(Class<?> clazz){
        if (clazz == null) return -1;
        return clazz.isInterface() ? getInterfaceDepth(clazz) : getClassDepth(clazz);
    }
}