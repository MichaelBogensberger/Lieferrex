package com.htlimst.lieferrex.dto;

import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.model.fragments.FragmentText;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FragmentTextEditDto {
    public FragmentText fragmentText;
    public Position position;
}
