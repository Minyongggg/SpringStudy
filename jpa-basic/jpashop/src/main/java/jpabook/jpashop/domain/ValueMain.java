package jpabook.jpashop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class ValueMain {

    public static void main(String[] args) {
        Hi hi = new Hi("minyong");

        System.out.println(hi);
        System.out.println(hi.getName());

    }
}

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
class Hi {

    private final String name;


}