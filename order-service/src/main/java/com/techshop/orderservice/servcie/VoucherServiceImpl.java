package com.techshop.orderservice.servcie;

import com.techshop.orderservice.dto.voucher.CreateVoucherDto;
import com.techshop.orderservice.dto.voucher.UpdateVoucherDto;
import com.techshop.orderservice.entity.Voucher;
import com.techshop.orderservice.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository repository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Voucher getVoucherById(Long voucherId) {
        Optional<Voucher> voucher = repository.findById(voucherId);

        if (!voucher.isPresent()) {
            throw new IllegalStateException("Voucher not exists");
        }

        return voucher.get();
    }

    @Override
    public List<Voucher> getVouchers() {
        return repository.findAll();
    }

    @Override
    public List<Voucher> getVoucherActive() {
        return repository.findByIsDeletedFalse();
    }

    @Override
    public Voucher createVoucher(CreateVoucherDto dto) {
        Voucher voucher = new Voucher();

        if(repository.existsByVoucherName(dto.getVoucherName()))
            throw new IllegalStateException("Voucher này đã tồn tại");

        voucher.setVoucherName(dto.getVoucherName());
        voucher.setVoucherDesc(dto.getVoucherDesc());
        voucher.setVoucherValue(dto.getVoucherValue());
        voucher.setAmount(dto.getAmount());
        voucher.setCappedAt(dto.getCappedAt());
        voucher.setValidDate(dto.getValidDate());
        voucher.setExpirationDate(dto.getExpirationDate());

        return repository.save(voucher);
    }

    @Override
    public Boolean deleteVoucher(Long voucherId) {
        Voucher voucher = getVoucherById(voucherId);

        voucher.setIsDeleted(true);

        repository.save(voucher);

        return true;
    }

    @Override
    public Voucher updateVoucher(UpdateVoucherDto dto) {
        Voucher voucher = getVoucherById(dto.getVoucherId());

        voucher.setVoucherName(dto.getVoucherName());
        voucher.setVoucherDesc(dto.getVoucherDesc());
        voucher.setVoucherValue(dto.getVoucherValue());
        voucher.setAmount(dto.getAmount());
        voucher.setValidDate(dto.getValidDate());
        voucher.setExpirationDate(dto.getExpirationDate());
        voucher.setCappedAt(dto.getCappedAt());

        return repository.save(voucher);
    }

    @Override
    public Voucher getVoucherByName(String name) {
        Optional<Voucher> voucher = repository.findByVoucherName(name);

        if (!voucher.isPresent()) {
            throw new IllegalStateException("Voucher not exists");
        }

        return voucher.get();
    }
}
