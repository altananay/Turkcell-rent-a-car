package kodlama.io.rentacar.business.rules;

import kodlama.io.rentacar.common.dto.CreateRentalPaymentRequest;
import kodlama.io.rentacar.core.exceptions.BusinessException;
import kodlama.io.rentacar.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentBusinessRules {

    private final PaymentRepository repository;

    public void checkIfPaymentIsValid(CreateRentalPaymentRequest request)
    {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(request.getCardNumber(), request.getCardHolder(), request.getCardExpirationYear(), request.getCardExpirationMonth(), request.getCardCvv()))
        {
            throw new BusinessException("Kart bilgileriniz hatalı");
        }
    }

    public void checkIfBalanceIsEnough(double balance, double price)
    {
        if (balance < price)
            throw new BusinessException("Yetersiz bakiye");
    }

    public void checkIfCardExists(String cardNumber)
    {
        if (repository.existsByCardNumber(cardNumber))
            throw new BusinessException("Kart numarası zaten kayıtlı");
    }

    public void checkIfPaymentExists(int id)
    {
        if (!repository.existsById(id))
            throw new BusinessException("Ödeme bilgisi bulunamadı");
    }

}
