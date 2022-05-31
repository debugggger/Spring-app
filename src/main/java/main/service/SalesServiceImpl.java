package main.service;

import javassist.NotFoundException;
import main.entity.Sales;
import main.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService{
    @Autowired
    private SalesRepository salesRepository;

    @Override
    public List<Sales> listSales() {
        return (List<Sales>) salesRepository.findAll();
    }

    @Override
    public Sales findSale(long id) throws NotFoundException {
        Optional<Sales> optionalSales = salesRepository.findById(id);
        if (optionalSales.isPresent()){
            return optionalSales.get();
        }
        else{
            throw new NotFoundException("Sale not found");
        }
    }

    @Override
    public void deleteSale(Long id) throws NotFoundException {
        if(!salesRepository.existsById(id)) {
            throw new NotFoundException("Sale not found");
        }
        salesRepository.deleteById(id);
    }

    @Override
    public Sales addSale(Sales sales) {
        return salesRepository.save(sales);
    }

    @Override
    public List<Sales> findSalesByGoodName(String name) {
        return salesRepository.findSalesWithName(name);
    }
}
